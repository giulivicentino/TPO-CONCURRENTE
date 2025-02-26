
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NadoDelfines {

    private int cambioPile = 1;
    private int cantPersonas = 0;
    private boolean ingreso = true;
    private boolean permiso = false;
    private int cantActualPile = 0;
    private Lock accesoPileta = new ReentrantLock(true);
    private Condition colEntrada = accesoPileta.newCondition();
    private Condition colFuncion = accesoPileta.newCondition();
    private Condition esperaControl = accesoPileta.newCondition();
<<<<<<< HEAD
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

            if (t.verificarHora()) {
                cantPersonas++;
                if (cantActualPile < 10) {
                    cantActualPile++;
                    System.out.println("CANTIDAD PERSONAS PILETA " + cambioPile + ": " + cantActualPile);
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
                    System.out.println("CAMBIO PILETA: " + cambioPile);
                }

                if (cambioPile == 4 && cantActualPile == 10) {
                    ingreso = false;
                }
                colFuncion.await();
            }

=======

    public void solPile() {
        try {
            accesoPileta.lock();
            while (!ingreso) {
                colEntrada.await();
            }
            cantPersonas++;
            if (cantActualPile < 10) {
                cantActualPile++;
                System.out.println("CANTIDAD PERSONAS PILETA " + cambioPile + ": " + cantActualPile);
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
                System.out.println("CAMBIO PILETA: " + cambioPile);
            }

            if (cambioPile == 4 && cantActualPile == 10) {
                ingreso = false;
            }
            colFuncion.await();
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
        } catch (Exception e) {

        } finally {
            accesoPileta.unlock();
        }
    }

    public void abandonarFuncion() {
        try {
            accesoPileta.lock();

            System.out.println("Visitante se va ");
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
        } catch (Exception e) {
            // TODO: handle exception
        }

        System.out.println("--- COMIENZA LA FUNCION NADO CON DELFINES ---");
        ingreso = false;
        accesoPileta.unlock();
    }

    public void terminarFuncion() {
        try {
            accesoPileta.lock();
<<<<<<< HEAD
            while (permiso) {
=======
            while(permiso){
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
                esperaControl.await();
            }
            System.out.println("--- FINALIZA LA FUNCION NADO CON DELFINES ---");
            cambioPile = 1;
            cantActualPile = 0;
            colFuncion.signalAll();
        } catch (Exception e) {
        } finally {
            accesoPileta.unlock();
        }

<<<<<<< HEAD
=======
    }

    public void avisaControlComienzo(){
        accesoPileta.lock();
        permiso = true; 
        esperaControl.signal();
        accesoPileta.unlock();
    }

    public void avisaControlFin(){
        accesoPileta.lock();
        permiso = false; 
        esperaControl.signal();
        accesoPileta.unlock();
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
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