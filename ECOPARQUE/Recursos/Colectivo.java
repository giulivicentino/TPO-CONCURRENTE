package Recursos;
import java.util.concurrent.Semaphore;

public class Colectivo {
        private Semaphore semCole1 = new Semaphore(2);
        private Semaphore semCole2 = new Semaphore(2);
        private Semaphore mutexSubir = new Semaphore(1,true);
        private Semaphore mutexBajar = new Semaphore(1,true);

public boolean subirCole() throws InterruptedException{
        mutexSubir.acquire();//para que no pisen la info
        boolean cole1;
        
        if(semCole1.availablePermits() > semCole2.availablePermits()){//si esta mas vacio el cole 1 que el cole 2
            semCole1.acquire();
            cole1=true;
        }else { //
            semCole2.acquire();
            cole1=false;
        }
        mutexSubir.release();
                return cole1;
    }

    public void bajarCole(boolean cole1) throws InterruptedException{
        mutexBajar.acquire();//para que no pisen la info
        if(cole1){//si fue por el 1
            semCole1.release();
        }else { //si fue por el 2
            semCole2.release();
        }
       mutexBajar.release();
    }


}