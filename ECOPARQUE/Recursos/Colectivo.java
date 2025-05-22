package Recursos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Colectivo {
    private int capacidadCole = 25;
    private boolean puedeSubir = true;
    private boolean puedeBajar = false;
    private int cantidadEnCole;
    private Tiempo t;
    private int id;

private Lock lockCole = new ReentrantLock(true);
    private Condition esperaSubir = lockCole.newCondition(); // cola de espera para cuando este el colectivo en la parada
    private Condition esperaBajar = lockCole.newCondition(); // cola de espera para cuando este el colectivo en el parque
    
private Condition esperaVolver = lockCole.newCondition(); //para que el colectivero espere a que se baje la gente

    public static final String VERDE = "\u001B[38;5;46m"; // Verde en la paleta de 256 colores
    public static final String ROJO = "\u001B[38;5;196m"; // Rojo en la paleta de 256 colores
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)

    public Colectivo(Tiempo tiempo, int id) {
        this.t = tiempo;
        this.id = id;
    }

    // metodos del visitante
    public boolean subirCole() throws InterruptedException {
        boolean subio = false;
       try {
        lockCole.lock();
        if (t.verificarIngreso()) {
            try {
                while (!puedeSubir && cantidadEnCole+1 == capacidadCole) { // no sube si esta lleno o si esta en viaje
                    esperaSubir.await(); // espera a que el colectivo vuelva
                }

                cantidadEnCole++;
                if (cantidadEnCole == capacidadCole) { // si era el ultimo lugar libre
                    puedeSubir = false;
                }
                subio = true;
                System.out.println(VERDE + "```` INGRESO POR COLECTIVO " + id + " ```` \n" + "La persona "
                        + Thread.currentThread().getName() + "se SUBE al colectivo , cantidad en cole: "
                        + cantidadEnCole + RESET);
            } catch (InterruptedException ex) {
            }
        } else {
            subio = false; // no sube por que ya es tarde
        } 
        
    } catch (Exception e) { } finally{
    lockCole.unlock();   
    }
       return subio;
    }

    public void bajarCole() throws InterruptedException {
        try {
        lockCole.lock();
       
            while (!puedeBajar) { // no baja si esta lleno o si esta en viaje
                esperaBajar.await(); // espera a que el colectivo llegue a destino
            }
            cantidadEnCole--;

            System.out.println(VERDE + "```` INGRESO POR COLECTIVO " + id + " ```` \n" + "La persona "
                    + Thread.currentThread().getName() + "se BAJA del colectivo,  cantidad en cole: " + cantidadEnCole
                    + RESET);
                    
                    if(cantidadEnCole == 0){ // si se bajaron todos
                        esperaVolver.signal(); // despierta al colectivero
                    System.out.println("El colectivo " + id + " se encuentra vacio, puede volver a la parada");
                    }

        } catch (Exception e) { } finally{
    lockCole.unlock();   
    }
    }

    // metodos del colectivero
    public void arrancarCole() throws InterruptedException {
        try {
            lockCole.lock();
            puedeSubir = false;
            puedeBajar = false;
            System.out.println(" ..... COLECTIVO " + id + "  EN VIAJE .....");
        } catch (Exception e) {
        } finally {
            lockCole.unlock();
        }
    }
 public void terminaViaje() {
        try {
             lockCole.lock();
            puedeBajar = true; // ya paso el tiempo en el colectivero
            esperaBajar.signalAll(); // despierta a los que estaban esperando para bajar
        
            while (cantidadEnCole != 0) { // espera a que se bajen todos para "reiniciar"
           esperaVolver.await();
        }
        } catch (Exception e) {
        } finally {
            lockCole.unlock();
        }
    }
    public void vueltaCole() throws InterruptedException {
         try {
            lockCole.lock();
        
        puedeSubir = true;
        System.out.println(" ..... COLECTIVO " + id + " VUELVE EXITOSAMENTE .....");
        esperaSubir.signalAll(); // despierta a los que estaban esperando para subir
        } catch (Exception e) {

} finally {
            lockCole.unlock();
        }

    }


   

}