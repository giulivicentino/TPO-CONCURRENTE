package Recursos;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MundoAventura { // recurso compartido por tirolesas, cuerdas y saltos
    
    public static final String VERDE = "\u001B[32m";//colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m";  //colores para la salida por pantalla (mas legible)

    private int cantSaltos;
    private int cuerda;
    private int cantEsperandoLado1;
    private int cantEsperandoLado2;
    private int cantTirolesas;
    private Lock accesoTirolesa;
    private Lock accesoCuerda;
    private Lock accesoSalto;
    private Condition colaTirolesa1; // representa un lado de la tirolesa
    private Condition colaTirolesa2; // representa el otro lado de la tirolesa
    private Condition colaSalto;
    private Condition colaCuerda;
    Semaphore semControl = new Semaphore(0);
    Tiempo t;

    public MundoAventura(Tiempo time) {
        this.cantSaltos = 2;
        this.cantTirolesas = 2;
        this.cuerda = 1;
        this.accesoTirolesa = new ReentrantLock(true);
        this.accesoCuerda = new ReentrantLock(true);
        this.accesoSalto = new ReentrantLock(true);
        colaTirolesa1 = accesoTirolesa.newCondition();
        colaTirolesa2 = accesoTirolesa.newCondition();
        colaCuerda = accesoCuerda.newCondition();
        colaSalto = accesoSalto.newCondition();
        this.t = time;
    }

    // TIROLESA

    public void subirTirolesa() {
        try {
            accesoTirolesa.lock();
            cantEsperandoLado1++;
            while (cantTirolesas == 0) {
                colaTirolesa1.await();
            }
            if (t.permisoRealizarActividad()) {
                cantTirolesas--;
                cantEsperandoLado1--;
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"
                                        +"La persona " + Thread.currentThread().getName()
                        + " se encuentra utilizando la tirolesa de ESTE a OESTE, tirolesas disponibles: "+ cantTirolesas+RESET);
            }

        } catch (InterruptedException e) {

        } finally {
            accesoTirolesa.unlock();
        }
    }

    public void subirTirolesa2() {
        try {
            accesoTirolesa.lock();
            cantEsperandoLado2++;
            while (cantTirolesas == 0) {
                colaTirolesa1.await();
            }
            if (t.permisoRealizarActividad()) {
                cantTirolesas--;
                cantEsperandoLado2--;
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"
                                        +"La persona " + Thread.currentThread().getName()+ " se encuentra utilizando la tirolesa de OESTE a ESTE, tirolesas disponibles: "
                                        + cantTirolesas+RESET);
            }

        } catch (InterruptedException e) {

        } finally {
            accesoTirolesa.unlock();
        }
    }

    public void bajarseTirolesa() {
        accesoTirolesa.lock();
        if (t.permisoRealizarActividad()) {
            cantTirolesas++;
            System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"
                                    +"La persona " + Thread.currentThread().getName()
                                    + " se bajó de la tirolesa de ESTE a OESTE, tirolesas disponibles: " + cantTirolesas+RESET);

            if (cantEsperandoLado2 > 0) {
                colaTirolesa2.signalAll();
            } else {
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"
                                        +"LADO OESTE VACIO"+RESET);
                semControl.release();
            }
        }
        accesoTirolesa.unlock();
    }

    public void bajarseTirolesa2() {
        accesoTirolesa.lock();
        if (t.permisoRealizarActividad()) {
            cantTirolesas++;
            System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"
                              +"La persona " + Thread.currentThread().getName()
                    + " se bajó de la tirolesa de OESTE a ESTE, tirolesas disponibles: " + cantTirolesas+RESET);

            if (cantEsperandoLado1 > 0) {
                colaTirolesa1.signalAll();
            } else {
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"LADO ESTE VACIO"+RESET);
                semControl.release();
            }
        }
        accesoTirolesa.unlock();
    }

    public void verficarTirolesa() { // metodo del control de la tirolesa
        try {
            semControl.acquire();
            if (cantEsperandoLado1 > 0) {
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"TIROLESA VA DE ESTE A OESTE"+RESET);
            } else {
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"TIROLESA VA DE OESTE A ESTE"+RESET);
            }

        } catch (InterruptedException e) {
        }

    }

    public void llevarTirolesa() { // metodo del control de la tirolesa
        accesoTirolesa.lock();
        colaTirolesa1.signalAll();
        colaTirolesa2.signalAll();
        accesoTirolesa.unlock();
    }

    // CUERDA

    public void usarCuerda(){
        accesoCuerda.lock();
            try {
                while(cuerda < 1){
                colaCuerda.await();
                }
                if(t.permisoRealizarActividad()){
                    cuerda--; 
                    System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"La persona "+Thread.currentThread().getName()+" se encuentra usando la cuerda"+RESET);
                    Thread.sleep(900);
                    System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"La persona "+Thread.currentThread().getName()+" dejó de usar la cuerda"+RESET);
                    cuerda++;
                    colaCuerda.signalAll();
                }
            } catch (InterruptedException e) {
            }finally{
                accesoCuerda.unlock();
            }   
    }

    // SALTO

    public void saltar(){
        accesoSalto.lock();
        try {
            while(cantSaltos == 0){
                colaSalto.await();
            }
            if(t.permisoRealizarActividad()){
                cantSaltos--; 
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"La persona "+Thread.currentThread().getName()+" está por saltar"+RESET);
                Thread.sleep(900);
                System.out.println(VERDE+"~~~~ MUNDO AVENTURA ~~~~ \n"+"La persona "+Thread.currentThread().getName()+" acaba de saltar"+RESET);
                cantSaltos++; 
                colaSalto.signalAll();
            }
        } catch (Exception e) {
        }finally{
            accesoSalto.unlock();
        }
    }

}