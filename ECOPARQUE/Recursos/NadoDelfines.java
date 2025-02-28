package Recursos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NadoDelfines {
    public static final String AZUL = "\u001B[34m";  //colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m";  //colores para la salida por pantalla (mas legible)

    private int cambioPile = 1;
    private int cantPersonas = 0;
    private boolean ingreso = true;
    private boolean permiso = false;
    private int cantActualPile = 0;
    private Lock accesoPileta = new ReentrantLock(true);
    private Condition colEntrada = accesoPileta.newCondition();
    private Condition colFuncion = accesoPileta.newCondition();
    private Condition esperaControl = accesoPileta.newCondition();
    private Tiempo t;

    public NadoDelfines(Tiempo time) {
        this.t = time;
    }

    public void solPile() {
        try {
            accesoPileta.lock();

            while (!ingreso) {
                colEntrada.await();
            }

            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 15)) {  //verifica hasta el ultimo horario que es a las 17:15
                cantPersonas++;
                if (cantActualPile < 10) {
                    cantActualPile++;
                    System.out.println(AZUL+"(+++++ NADO DELFINES +++++ \n" 
                                      +"+++ CANTIDAD PERSONAS PILETA: " + cambioPile + ": " + cantActualPile+")"+RESET);
                } else {
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
                    System.out.println(AZUL+"(+++++NADO DELFINES+++++ \n"
                                      +"+++ CAMBIO PILETA: " + cambioPile+")"+RESET);
                }

                if (cambioPile == 4 && cantActualPile == 10) {
                    ingreso = false;
                }
                colFuncion.await();
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
                colEntrada.signalAll();
            } else {
                colFuncion.signalAll();
            }
        } catch (Exception e) {
        } finally {
            accesoPileta.unlock();
        }
    }

    public void comenzarFuncion() {

        try {
            accesoPileta.lock();
            while (cambioPile != 4 || !permiso) {
                esperaControl.await();
            }
            if(t.verificarHora()){
                System.out.println(AZUL+"(+++++ NADO DELFINES +++++ \n" 
                                  +"--- COMIENZA LA FUNCION NADO CON DELFINES ---)"+RESET);
                ingreso = false;
            }
        } catch (Exception e) {
        }finally{
            accesoPileta.unlock();
        }
    }

    public void terminarFuncion() {
        try {
            accesoPileta.lock();
            while (permiso) {
                esperaControl.await();
            }
            if(t.verificarHora()){
                System.out.println(AZUL+"(+++++ NADO DELFINES +++++ \n"+
                                   "--- FINALIZA LA FUNCION NADO CON DELFINES ---)"+RESET);
                cambioPile = 1;
                cantActualPile = 0;
                colFuncion.signalAll();
            }
        } catch (Exception e) {
        } finally {
            accesoPileta.unlock();
        }

    }

    public void avisaControlComienzo() {
        accesoPileta.lock();
        permiso = true;
        esperaControl.signal();
        accesoPileta.unlock();
    }

    public void avisaControlFin() {
        accesoPileta.lock();
        permiso = false;
        esperaControl.signal();
        accesoPileta.unlock();
    }
}