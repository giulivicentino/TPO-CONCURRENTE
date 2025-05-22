package Recursos;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NadoDelfines {
    public static final String AZUL = "\u001B[34m"; // colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)

    private int cambioPile = 1;
    private int cantPersonas = 0;
    private boolean ingreso = true;
    private boolean permiso = false;
    private int cantActualPile = 0;
    private Lock accesoPileta = new ReentrantLock(true);
    private Condition colEntrada = accesoPileta.newCondition(); // cola de espera para ingresar a funcion
    private Condition colFuncion = accesoPileta.newCondition(); // variable de condicion que representa los visitantes
                                                                // en función
    private Condition esperaControl = accesoPileta.newCondition(); // variable de condicion para el control de la
                                                                   // función
    private Tiempo t;

    public NadoDelfines(Tiempo time) {
        this.t = time;
    }

    // Métodos de los visitante
    public void solPile() {
        try {
            accesoPileta.lock();

            while (!ingreso) {
                colEntrada.await();
            }

            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 15)) { // verifica hasta el ultimo horario que
                                                                                 // es a las 17:15
                cantPersonas++;
                if (cantActualPile < 10) {
                    cantActualPile++;
                    System.out.println(AZUL + "(+++++ NADO DELFINES +++++ \n"
                            + "+++ CANTIDAD PERSONAS PILETA: " + cambioPile + ": " + cantActualPile + ")" + RESET);
                } else {
                    // control de cambio de piletas
                    switch (cambioPile) {
                        case 1:
                            cambioPile = 2;
                            break;
                        case 2:
                            cambioPile = 3;
                            break;
                        case 3:
                            cambioPile = 4;
                            break;
                        case 4:
                            cambioPile = 1;
                            break;
                    }
                    cantActualPile = 0;
                    System.out.println(AZUL + "(+++++NADO DELFINES+++++ \n"
                            + "+++ CAMBIO PILETA: " + cambioPile + ")" + RESET);
                }

                if (cambioPile == 4 && cantActualPile == 10) {
                    ingreso = false;
                }

                colFuncion.await(30, TimeUnit.SECONDS); // tiempo de espera si la función no se llena

                if (cantActualPile != 0) { // Caso visitante se cansa de esperar la función
                    System.out.println(AZUL + "+++++NADO DELFINES+++++ \n"
                            + "+++ Visitante se cansó de esperar" + RESET);
                    cantActualPile--;
                }

            }

        } catch (Exception e) {

        } finally {
            accesoPileta.unlock();
        }
    }

    public void abandonarFuncion() {
        try {
            accesoPileta.lock();

            cantPersonas--;
            if (cantPersonas == 0) {
                ingreso = true;
                // una vez que se vacían todas las piletas, se permite el ingreso nuevamente
                colEntrada.signalAll();
            } else {
                colFuncion.signal();
            }
        } catch (Exception e) {
        } finally {
            accesoPileta.unlock();
        }
    }

    // Metodos del control de las piletas
    public void comenzarFuncion() {

        try {
            accesoPileta.lock();
            while (cambioPile != 4 || !permiso) {
                esperaControl.await();
            }
            if (t.verificarHora()) { // verifica si hay tiempo para iniciar función
                System.out.println(AZUL + "(+++++ NADO DELFINES +++++ \n"
                        + "--- COMIENZA LA FUNCION NADO CON DELFINES ---)" + RESET);
                ingreso = false;
            }
        } catch (Exception e) {
        } finally {
            accesoPileta.unlock();
        }
    }

    public void terminarFuncion() {
        try {
            accesoPileta.lock();
            while (permiso) {
                esperaControl.await();
            }
            System.out.println(AZUL + "(+++++ NADO DELFINES +++++ \n" +
                    "--- FINALIZA LA FUNCION NADO CON DELFINES ---)" + RESET);
            // Cambia ingreso a pileta 1 y avisa para que los visitantes abandonen la
            // función
            cambioPile = 1;
            cantActualPile = 0;
            colFuncion.signal();

        } catch (Exception e) {
        } finally {
            accesoPileta.unlock();
        }

    }

    // Metodos del control tiempo
    public void avisaControlComienzo() { // metodo para avisar a control para arrancar función
        accesoPileta.lock();
        permiso = true;
        esperaControl.signal();
        accesoPileta.unlock();
    }

    public void avisaControlFin() { // metodo para avisar a control para terminar función
        accesoPileta.lock();
        permiso = false;
        esperaControl.signal();
        accesoPileta.unlock();
    }
}