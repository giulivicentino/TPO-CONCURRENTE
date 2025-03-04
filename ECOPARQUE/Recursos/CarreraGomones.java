package Recursos;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CarreraGomones {
    public static final String AMARILLO = "\u001B[33m"; //colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m";  //colores para la salida por pantalla (mas legible)

    private int cantidadTren;
    private int cantidadSubidoTren;
    private boolean destino = false;
    private boolean accesoTren = true;
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

    public synchronized void subirTren() {
        try {
            while (cantidadSubidoTren == cantidadTren) {
                this.wait();
            }
            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
                cantidadSubidoTren++;
                System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"EL visitante " + Thread.currentThread().getName()
                        + " se subió al tren, cantidad de personas subidas al tren; " + cantidadSubidoTren+RESET);
                this.notifyAll();
                bajarTren(accesoTren);
            }else{
                accesoTren = false; 
            }
        } catch (Exception e) {
        }
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
            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
                System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"ARRANCÓ EL TREN"+RESET);
            }else{
                accesoTrenControl = false; 
            }
        } catch (Exception e) {
        }
        return accesoTrenControl; 
    }

    public synchronized void finalizarRecorridoTren(boolean acceso) {
        if(acceso){
            System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"EL TREN LLEGÓ A LA CARRERA DE GOMONES"+RESET);
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
                    System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"EL TREN SE ENCUENTRA DISPONIBLE"+RESET);
                    espaciosOcupadosTren = 0;
                    cantidadSubidoTren = 0;
                    destino = false;
                    this.notifyAll();
                
            } catch (Exception e) {
            }
        }
        
    }

    // Visitantes que eligen bicicletas

    public void subirBici() {
        try {
            if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 40)) {
                semBicis.acquire();
                System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"El visitante " + Thread.currentThread().getName() + " tomó una bici"+RESET);
            }
        } catch (InterruptedException e) {
        }
    }

    public void dejarBici() {
        if (t.permisoRealizarActividad()) {
            System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+
                    "El visitante " + Thread.currentThread().getName()
                            + " llegó a la carreras de gomones con bicicleta"+RESET);
            semBicis.release();
        }
    }

    // Metodos para elegir el tipo de gomón para la carrera

    public boolean elegirGomon(boolean eleccionGomon) throws InterruptedException {
        boolean corredor = true;
        if (t.permisoRealizarActividad()) {
            if (eleccionGomon) {
                // eleccion gomon doble
                if (!esperaCompanero.isEmpty()) {
                    // condicion hay personas esperando
                    esperaCompanero.take();
                    System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"Gomon doble preparado"+RESET);
                    corredor = true;
                } else {
                    gomonesDobles.take(); // simulacion de espera de compañero para gomon doble
                    if(t.permisoRealizarActividad()){ //condicion de visitante esperando gomon pero la actividad ya cerró
                    System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+
                            "La persona " + Thread.currentThread().getName()
                                    + " agarra un gomon doble, espera compañero"+RESET);
                    int i = 1; // simulacion gomon
                    esperaCompanero.add(i);
                    corredor = false;
                    }
                }

            } else {
                // eleccion gomon simple
                gomonesIndivuales.take();
                if(t.permisoRealizarActividad()){ //condicion de visitante esperando gomon pero la actividad ya cerró
                    System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+
                                      "La persona " + Thread.currentThread().getName() + " agarra un gomon individual"+RESET);
                    corredor = true;
                }
            }

        } 
        return corredor;
    }

    public void carrera() throws InterruptedException, BrokenBarrierException {
        if (t.permisoRealizarActividad()) {
            try {
                // Visitantes esperan para correr
                barreraInicio.await(5, TimeUnit.SECONDS);
                    System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"EN CARRERA"+RESET);
                    Thread.sleep(1200);
                    System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"FINALIZA CARRERA"+RESET);

            } catch (TimeoutException e) {
                // Si pasa determinado tiempo sin poder conseguir la cantidad necesaria, el
                // visitante se vá
                System.out.println(AMARILLO+"```` CARRERA GOMONES ```` \n"+"Un visitante se cansó de esperar para la carrera"+RESET);
            }
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

}