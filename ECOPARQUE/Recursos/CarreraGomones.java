package Recursos;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarreraGomones {
    public static final String AMARILLO = "\u001B[33m"; // colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)

    private int cantidadTren;
    private int cantidadSubidoTren;
    private boolean destino = false;
    private boolean accesoTrenControl = true;
    private Lock accederTren;
    private Condition colaEsperaTren;
    private Condition esperaDestino;
    private Condition controlTren;
    private Semaphore semBicis;
    private Semaphore semGomonDoble;
    private final int cantidadTotalCarrera = 5;
    private int espaciosDesocupadosTren;
    Tiempo t;

    // Gomones
    private BlockingQueue<Object> gomonesIndivuales = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> gomonesDobles = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> esperaCompanero = new LinkedBlockingQueue<>();
    private CyclicBarrier barreraInicio = new CyclicBarrier(cantidadTotalCarrera);

    public CarreraGomones(Tiempo time) {
        cantidadTren = 5;
        semBicis = new Semaphore(15, true);
        semGomonDoble = new Semaphore(0);
        this.accederTren = new ReentrantLock();
        colaEsperaTren = accederTren.newCondition();
        controlTren = accederTren.newCondition();
        esperaDestino = accederTren.newCondition();

        for (int i = 0; i < 5; i++) {
            gomonesDobles.add(i);
            gomonesIndivuales.add(i);
        }
        t = time;

    }

    // metodos usados por visitantes para elegir medio de transporte
    public void subirTren() {
        accederTren.lock();
        try {
            while (cantidadSubidoTren == cantidadTren) {
                colaEsperaTren.await();
            }

            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
                cantidadSubidoTren++;
                System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + "EL visitante "
                        + Thread.currentThread().getName()
                        + " se subió al tren, cantidad de personas subidas al tren; " + cantidadSubidoTren + RESET);
                if (cantidadSubidoTren == cantidadTren) {
                    controlTren.signal();
                }
                bajarTren();
            } else {
                if (cantidadSubidoTren > 0) {
                    destino = true;
                    esperaDestino.signal();
                }
                colaEsperaTren.signal();
            }

        } catch (Exception e) {
        } finally {
            accederTren.unlock();
        }

    }

    public void bajarTren() throws InterruptedException {
        accederTren.lock();
        while (!destino) {
            esperaDestino.await();
        }
        if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
            espaciosDesocupadosTren++;
            if (espaciosDesocupadosTren == cantidadTren) {
                controlTren.signal();
            } else {
                esperaDestino.signal();
            }

        } else {
            cantidadSubidoTren--;
            if (cantidadSubidoTren == 0) {
                colaEsperaTren.signal();
            } else {
                esperaDestino.signal();
            }
        }
        accederTren.unlock();
    }

    // metodo del control del tren
    public boolean arrancarTren() {
        accederTren.lock();
        try {
            while (cantidadSubidoTren < cantidadTren) {
                controlTren.await();
            }
            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
                System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + "ARRANCÓ EL TREN" + RESET);
            } else {
                accesoTrenControl = false;
            }
        } catch (Exception e) {
        } finally {
            accederTren.unlock();
        }
        return accesoTrenControl;
    }

    public void finalizarRecorridoTren(boolean acceso) {
        accederTren.lock();
        if (acceso) {
            System.out.println(
                    AMARILLO + "```` CARRERA GOMONES ```` \n" + "EL TREN LLEGÓ A LA CARRERA DE GOMONES" + RESET);
            destino = true;
            esperaDestino.signal();
        }
        accederTren.unlock();
    }

    public void vueltaTren(boolean acceso) {
        accederTren.lock();
        try {
            while (espaciosDesocupadosTren < cantidadTren) {
                controlTren.await();
            }
            System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + "EL TREN SE ENCUENTRA DISPONIBLE" + RESET);
            espaciosDesocupadosTren = 0;
            cantidadSubidoTren = 0;
            destino = false;
            colaEsperaTren.signal();

        } catch (Exception e) {
        } finally {
            accederTren.unlock();
        }
    }

    // Visitantes que eligen bicicletas
    public void subirBici() {
        try {
            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
                semBicis.acquire();

                System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + "El visitante "
                        + Thread.currentThread().getName() + " tomó una bici" + RESET);

                Thread.sleep(1800);

                System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" +
                        "El visitante " + Thread.currentThread().getName()
                        + " llegó a la carreras de gomones con bicicleta" + RESET);
                semBicis.release();
            }

        } catch (InterruptedException e) {
        }
    }

    // Método para elegir los gomones
    public synchronized boolean elegirGomon(boolean eleccionGomon) throws InterruptedException {

        boolean corredor = true;
        if (t.permisoRealizarActividad()) {

            if (eleccionGomon) {
                // Elección gomón doble
                Object compañero = esperaCompanero.poll();
                if (compañero != null) {
                    if (t.permisoRealizarActividad()) {
                        corredor = true;
                        System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + " La persona "
                                + Thread.currentThread().getName()
                                + " elige gomon doble, GOMON DOBLE PREPARADO " + corredor + RESET);
                    } else {
                        semGomonDoble.release(); // condición compañero de gomón doble esperando para la carrera
                    }

                } else {
                    gomonesDobles.take(); // Simulación de espera de compañero
                    if (t.permisoRealizarActividad()) {
                        System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" +
                                "La persona " + Thread.currentThread().getName()
                                + " agarra un gomon doble, espera compañero" + RESET);
                        esperaCompanero.put(1);
                        corredor = false;
                    } else {
                        gomonesDobles.put(1);
                    }
                }

            } else {
                // Elección gomón simple
                gomonesIndivuales.take();
                if (t.permisoRealizarActividad()) {
                    corredor = true;
                    System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" +
                            "La persona " + Thread.currentThread().getName()
                            + " agarra un gomon individual " + corredor + RESET);
                } else {
                    gomonesIndivuales.add(1);
                }
            }

        }
        return corredor;
    }

    // Método de simulación de la carrera
    public void carrera(boolean gomonListo, boolean doble) throws InterruptedException, BrokenBarrierException {

        if (t.permisoRealizarActividad()) {
            if (gomonListo) {
                try {
                    // Visitantes esperan para correr
                    barreraInicio.await(5, TimeUnit.SECONDS);
                    System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + "EN CARRERA" + RESET);
                    Thread.sleep(1200);
                    System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" + " FINALIZA ACTIVIDAD" + RESET);

                } catch (TimeoutException | BrokenBarrierException e) {
                    // Si pasa determinado tiempo sin poder conseguir la cantidad necesaria, el
                    // visitante se vá
                    System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n"
                            + "Un visitante se cansó de esperar para la carrera" + RESET);

                    synchronized (this) {
                        if (barreraInicio.isBroken()) {
                            System.out.println(AMARILLO + "⚠️ La barrera está rota. Se va a reiniciar." + RESET);
                            barreraInicio = new CyclicBarrier(5);
                        }
                    }
                }

                // devuelve un un tipo de gomón
                if (doble) {
                    gomonesDobles.put(1);
                    semGomonDoble.release();
                } else {
                    gomonesIndivuales.put(1);
                    ;
                }

            } else {
                semGomonDoble.acquire();
            }

            System.out.println(AMARILLO + "```` CARRERA GOMONES ```` \n" +
                    "La persona " + Thread.currentThread().getName() + " abandona la actividad" + RESET);
        }
    }

}