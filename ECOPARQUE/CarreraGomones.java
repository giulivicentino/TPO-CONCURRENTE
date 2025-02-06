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

    private int cantidadTren;
    private int cantidadSubidoTren;
    private Lock accesoTren;
    private Condition colaTren;
    private Condition esperaControlTren;
    private Semaphore semBicis;
    private final int cantidadTotalCarrera = 5;

    // Gomones
    private BlockingQueue<Object> gomonesIndivuales = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> gomonesDobles = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> esperaCompanero = new LinkedBlockingQueue<>();
    private final CyclicBarrier barreraInicio = new CyclicBarrier(cantidadTotalCarrera);

    public CarreraGomones() {
        cantidadTren = 5;
        this.accesoTren = new ReentrantLock(true);
        colaTren = accesoTren.newCondition();
        esperaControlTren = accesoTren.newCondition();
        semBicis = new Semaphore(15, true);

        for (int i = 0; i < 5; i++) {
            gomonesDobles.add(i);
            gomonesIndivuales.add(i);
        }

    }

    // metodos usados por visitantes para elegir medio de transporte

    public synchronized void subirTren() {
        try {
            while (cantidadSubidoTren == cantidadTren) {
                this.wait();
            }
        } catch (Exception e) {
        }
        cantidadSubidoTren++;
        System.out.println("EL visitante " + Thread.currentThread().getName()
                + " se subió al tren, cantidad de personas subidas al tren; " + cantidadSubidoTren);
        this.notifyAll();
    }

    // metodo del control del tren

    public synchronized void arrancarTren() {
        try {
            while (cantidadSubidoTren < cantidadTren) {
                System.out.println("control espera");
                this.wait();
            }

        } catch (Exception e) {
        }
        System.out.println("ARRANCÓ EL TREN");

    }

    public synchronized void finalizarRecorridoTren() {
        System.out.println("EL TREN LLEGÓ A LA CARRERA DE GOMONES");
        cantidadSubidoTren = 0;
        this.notifyAll();
    }

    // Visitantes que eligen bicicletas

    public void subirBici() {
        try {
            semBicis.acquire();
            System.out.println("El visitante " + Thread.currentThread().getName() + " tomó una bici");
        } catch (InterruptedException e) {
        }

    }

    public void dejarBici() {
        System.out.println(
                "El visitante " + Thread.currentThread().getName() + " llegó a la carreras de gomones con bicicleta");
        semBicis.release();
    }

    // Metodos para elegir el tipo de gomón para la carrera

    public boolean elegirGomon(boolean eleccionGomon) throws InterruptedException {
        boolean corredor;
        if (eleccionGomon) {
            // eleccion gomon doble
            if (!esperaCompanero.isEmpty()) {
                // condicion hay personas esperando
                esperaCompanero.take();
                System.out.println("Gomon doble preparado");
                corredor = true;

            } else {
                gomonesDobles.take(); // simulacion de espera de compañero para gomon doble
                System.out.println(
                        "La persona " + Thread.currentThread().getName() + " agarra un gomon doble, espera compa");
                int i = 1; // simulacion gomon
                esperaCompanero.add(i);
                corredor = false;
            }
        } else {
            // eleccion gomon simple
            gomonesIndivuales.take();
            System.out.println("La persona " + Thread.currentThread().getName() + " agarra un gomon individual");
            corredor = true;
        }

        return corredor;
    }

    public void carrera() throws InterruptedException, BrokenBarrierException{
        try {
            //Visitantes esperan para correr
            barreraInicio.await(5, TimeUnit.SECONDS);
            
            System.out.println("EN CARRERA");
            Thread.sleep(2000);
            System.out.println("FINALIZA CARRERA");

        } catch (TimeoutException e) {
            //Si pasa determinado tiempo sin poder conseguir la cantidad necesaria, el visitante se vá
            System.out.println("Un visitante se cansó de esperar para la carrera");
        }

    }

    public synchronized void devolverGomon(boolean eleccion) {

        int i = 1; // simulacion gomon

        if (eleccion) {
            gomonesDobles.add(i);
        } else {
            gomonesIndivuales.add(i);
        }
    }

public void subirGomonSimple(){

}

public void subirGomonDoble(){
    
}

}
