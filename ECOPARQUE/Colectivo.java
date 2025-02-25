import java.util.concurrent.Semaphore;

public class Colectivo {
    
        private int cantidadEsperando;
        private int cantEnCole1;
        private int cantEnCole2;

        private Semaphore semCole1 = new Semaphore(5);
        private Semaphore semCole2 = new Semaphore(5);
        private Semaphore terminal = new Semaphore(1,true);
        
public boolean subirCole() throws InterruptedException{
        terminal.acquire();//para que no pisen la info
        boolean cole1;
        System.out.println("soy "+ Thread.currentThread().getName()+"--- Permisos en sem1: "+semCole1.availablePermits()+" permisos en sem2: "+semCole2.availablePermits());
        if(semCole1.availablePermits() < semCole2.availablePermits()){//si esta mas vacio el cole 1 que el cole 2
            semCole1.acquire();
            System.out.println("soy "+ Thread.currentThread().getName()+" me subi al cole 1");
            cole1=true;
        }else { //esta lleno el 2 y hay lugar en el 1
            semCole2.acquire();
            System.out.println("soy "+ Thread.currentThread().getName()+" me subi al cole 2");
            cole1=false;
        }
        terminal.release();
                return cole1;
    }

    public void bajarCole(boolean cole1) throws InterruptedException{
        terminal.acquire();//para que no pisen la info
        
        if(cole1){//si fue por el 1
            semCole1.release();
            System.out.println("soy "+ Thread.currentThread().getName()+" me baje del cole 1");
           
        }else { //si fue por el 2
            semCole2.release();
            System.out.println("soy "+ Thread.currentThread().getName()+" me baje del cole 2");
            
        }System.out.println("soy "+ Thread.currentThread().getName()+"--- Permisos en sem1: "+semCole1.availablePermits()+" permisos en sem2: "+semCole2.availablePermits());
        terminal.release();
    }


}
