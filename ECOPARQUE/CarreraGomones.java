import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CarreraGomones {

    private int cantidadTren;
    private int cantidadSubidoTren;
    private boolean destino = false;
    private boolean accesoTren = true;
    private boolean accesoCarrera = true; 
    private boolean accesoBici = true; 
    private boolean accesoTrenControl = true; 
    private Semaphore semBicis;
    private final int cantidadTotalCarrera = 5;
    private int espaciosOcupadosTren;
    Tiempo t;

    // Gomones
    private BlockingQueue<Object> gomonesIndivuales = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> gomonesDobles = new LinkedBlockingQueue<>();
    private BlockingQueue<Object> esperaCompanero = new LinkedBlockingQueue<>();
    private final CyclicBarrier barreraInicio = new CyclicBarrier(cantidadTotalCarrera);

    public CarreraGomones(Tiempo time) {
        cantidadTren = 5;
        semBicis = new Semaphore(15, true);

        for (int i = 0; i < 5; i++) {
            gomonesDobles.add(i);
            gomonesIndivuales.add(i);
        }
        t = time;

    }

    // metodos usados por visitantes para elegir medio de transporte

    public synchronized boolean subirTren() {
        try {
            while (cantidadSubidoTren == cantidadTren) {
                this.wait();
            }
            if (t.getHora() < 10 || (t.getHora() == 10 && t.getMinuto() < 40)) {
                cantidadSubidoTren++;
                System.out.println("EL visitante " + Thread.currentThread().getName()
                        + " se subió al tren, cantidad de personas subidas al tren; " + cantidadSubidoTren);
                this.notifyAll();
            }else{
                accesoTren = false; 
            }
        } catch (Exception e) {
        }

        return accesoTren; 
    }

    public synchronized void bajarTren(boolean acceso) {
        if(acceso){
            try {
                while (!destino) {
                    this.wait();
                }
                espaciosOcupadosTren++;
                this.notifyAll();

        } catch (Exception e) {
        }
        }
    }

    // metodo del control del tren

    public synchronized boolean arrancarTren() {
        try {
            while (cantidadSubidoTren < cantidadTren) {
                this.wait();
            }
            if (t.getHora() < 10 || (t.getHora() == 10 && t.getMinuto() < 40)) {
                System.out.println("ARRANCÓ EL TREN");
            }else{
                accesoTrenControl = false; 
            }
        } catch (Exception e) {
        }
        return accesoTrenControl; 
    }

    public synchronized void finalizarRecorridoTren(boolean acceso) {
        if(acceso){
            System.out.println("EL TREN LLEGÓ A LA CARRERA DE GOMONES");
            destino = true;
            this.notifyAll();
        }
    }

    public synchronized void vueltaTren(boolean acceso) {
        if(acceso){
            try {
                while (espaciosOcupadosTren < cantidadTren) {
                    this.wait();
                }
                    System.out.println("EL TREN SE ENCUENTRA DISPONIBLE");
                    espaciosOcupadosTren = 0;
                    cantidadSubidoTren = 0;
                    destino = false;
                    this.notifyAll();
                
            } catch (Exception e) {
            }
        }
        
    }

    // Visitantes que eligen bicicletas

    public boolean subirBici() {
        try {
            if (t.getHora() < 10 || (t.getHora() == 10 && t.getMinuto() < 40)) {
                semBicis.acquire();
                System.out.println("El visitante " + Thread.currentThread().getName() + " tomó una bici");
            }else{
                accesoBici = false; 
            }
        } catch (InterruptedException e) {
        }
        return accesoBici; 
    }

    public void dejarBici(boolean permiso) {
        if (accesoBici) {
            System.out.println(
                    "El visitante " + Thread.currentThread().getName()
                            + " llegó a la carreras de gomones con bicicleta");
            semBicis.release();
        }
    }

    // Metodos para elegir el tipo de gomón para la carrera

    public boolean elegirGomon(boolean eleccionGomon) throws InterruptedException {
        boolean corredor = true;

        if (eleccionGomon) {
            if (t.getHora() < 10 || (t.getHora() == 10 && t.getMinuto() < 40)) {
                // eleccion gomon doble
                if (!esperaCompanero.isEmpty()) {
                    // condicion hay personas esperando
                    esperaCompanero.take();
                    System.out.println("Gomon doble preparado");
                    corredor = true;
                }else {
                    gomonesDobles.take(); // simulacion de espera de compañero para gomon doble
                    System.out.println(
                            "La persona " + Thread.currentThread().getName() + " agarra un gomon doble, espera compañero");
                    int i = 1; // simulacion gomon
                    esperaCompanero.add(i);
                    corredor = false;
               }
            }else{
                accesoCarrera = false; 
            }
        } else {
            if (t.getHora() < 10 || (t.getHora() == 10 && t.getMinuto() < 40)) {
                // eleccion gomon simple
                gomonesIndivuales.take();
                System.out.println("La persona " + Thread.currentThread().getName() + " agarra un gomon individual");
                corredor = true;
            }else {
                accesoCarrera = false; 
            }

        }

        return corredor;
    }

    public void carrera() throws InterruptedException, BrokenBarrierException {
        if(accesoCarrera){
            try {
                // Visitantes esperan para correr
                barreraInicio.await(5, TimeUnit.SECONDS);
                if (t.getHora() < 10 || (t.getHora() == 10 && t.getMinuto() < 40)) {
                    System.out.println("EN CARRERA");
                    Thread.sleep(200);
                    System.out.println("FINALIZA CARRERA");
                }
    
            } catch (TimeoutException e) {
                // Si pasa determinado tiempo sin poder conseguir la cantidad necesaria, el
                // visitante se vá
                System.out.println("Un visitante se cansó de esperar para la carrera");
            }
        }

    }

    public synchronized void devolverGomon(boolean eleccion) {
        if(accesoCarrera){
            int i = 1; // simulacion gomon
            if (eleccion) {
                gomonesDobles.add(i);
            } else {
                gomonesIndivuales.add(i);
            }   
        }
    }

}