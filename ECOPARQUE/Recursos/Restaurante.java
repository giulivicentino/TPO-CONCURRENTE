package Recursos;

import java.util.HashMap;
import Hilos.Persona;

public class Restaurante {
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)
    public static final String ROJO = "\u001B[31m"; // colores para la salida por pantalla (mas legible)

    private int cantActualResto = 0;
    private int id;
    private int capacidad;
    private HashMap<Persona, boolean[]> personas = new HashMap<>(); // <persona1, [almorzó,merendó]>
    Tiempo t;

    public Restaurante(int unId, int cantMax, Tiempo time) {
        id = unId;
        capacidad = cantMax;
        t = time;
    }

    public synchronized void entraRestaurante(Persona personita, int comida) {
        if (t.permisoRealizarActividad()) {
            try {
                if (!personas.containsKey(personita)) {
                    personas.put(personita, new boolean[] { false, false }); // agrega persona a la fila de ese
                                                                             // restaurante
                }
                while (cantActualResto == capacidad) {
                    this.wait();
                }
                if (t.permisoRealizarActividad()) {
                    cantActualResto++;
                    System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName()
                            + " INGRESÓ a RESTAURANTE -" + id
                            + "- , cantActual: " + cantActualResto + " max: " + capacidad + RESET);

                }
                this.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void pedirAlmuerzo(Persona personita) {
        if (t.permisoRealizarActividad()) {
            if (!personas.get(personita)[0]) { // verifica si la persona ya almorzó
                System.out.println(ROJO + ".... RESTAURANTE .... \n" +
                        Thread.currentThread().getName() + " se encuentra almorzando en: " + id + RESET);
                personas.get(personita)[0] = true;
            }
            this.notifyAll();
        }
        // salirRestaurante();
    }

    public synchronized void pedirMerienda(Persona personita) {
        if (t.permisoRealizarActividad()) {
            if (!personas.get(personita)[1]) { // verifica si la persona ya merendó
                System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName()
                        + " merendando ------- EN: " + id + RESET);
                personas.get(personita)[1] = true;
            }
            this.notifyAll();
        }
    }

    public synchronized void salirRestaurante() {
        if (t.permisoRealizarActividad()) {
            cantActualResto--;
            System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName()
                    + " ME FUI de RESTO -" + id + "- , cantActual: "
                    + cantActualResto + " max: " + capacidad + RESET);
            this.notifyAll();
        } else {
            if (cantActualResto > 0) {
                cantActualResto--;
                System.out.println(ROJO + ".... RESTAURANTE .... \n" + Thread.currentThread().getName()
                        + " ME FUI de RESTO -" + id + "- , cantActual: "
                        + cantActualResto + " max: " + capacidad + RESET);
            }
            this.notifyAll();
        }
    }

}