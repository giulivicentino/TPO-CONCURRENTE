package Recursos;

import java.util.HashMap;
import java.util.concurrent.Semaphore;


import Hilos.Persona;

public class Restaurante {
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)
    public static final String ROJO = "\u001B[31m"; // colores para la salida por pantalla (mas legible)

    private int id;
    private Semaphore semVisitantes;
    private HashMap<Persona, boolean[]> personas = new HashMap<>(); // <persona1, [almorzó,merendó]>
    Tiempo t;

    public Restaurante(int unId, int cantMax, Tiempo time) {
        id = unId;
        t = time;
        semVisitantes = new Semaphore(cantMax);
    }

    public void entrarRestaurante(Persona personita, int comida) {
        try {

            if (!personas.containsKey(personita)) {
                personas.put(personita, new boolean[] { false, false }); // agrega persona a la fila de ese restaurante
            }

            semVisitantes.acquire();

            if (t.permisoRealizarActividad()) {
                System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName()
                + " INGRESÓ a RESTAURANTE -" + id + RESET);
        
            switch (comida) {
                case 1:
                    pedirAlmuerzo(personita);
                    break;
            
                case 2:
                    pedirMerienda(personita);
                    break;
            }

            }else{
                semVisitantes.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void pedirAlmuerzo(Persona personita) throws InterruptedException {

        if (t.permisoRealizarActividad()) {
            if (!personas.get(personita)[0]) { // verifica si la persona ya almorzó
                try {
                    System.out.println(ROJO+".... RESTAURANTE .... \n"+
                            Thread.currentThread().getName() + " se encuentra almorzando en: " + id+RESET);
                    Thread.sleep(1200);
                    personas.get(personita)[0] = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            salirRestaurante();
    }

}

    public synchronized void pedirMerienda(Persona personita) throws InterruptedException {
        if (t.verificarHora()) {
            if (!personas.get(personita)[1]) { // verifica si la persona ya merendó
                try {

                    System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName()
                            + " merendando ------- EN: " + id + RESET);
                    Thread.sleep(1200);
                    personas.get(personita)[1] = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            salirRestaurante();
        }

    }

    public void salirRestaurante() throws InterruptedException {
        // metodo que modifica la cantidad actual de restaurante
            System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName() + " ME FUI de RESTO -"
            + id + RESET); 
        
        semVisitantes.release();
    }

}