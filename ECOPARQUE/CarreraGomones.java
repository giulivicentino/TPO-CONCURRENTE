import java.util.concurrent.Semaphore;
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

public CarreraGomones(){
    cantidadTren = 5; 
    this.accesoTren = new ReentrantLock(true); 
    colaTren = accesoTren.newCondition(); 
    esperaControlTren = accesoTren.newCondition(); 
    semBicis = new Semaphore(15); 
}


//metodos usados por visitantes para elegir medio de transporte

public synchronized void subirTren(){
    try {
        while(cantidadSubidoTren == cantidadTren){
            this.wait();
        }
    } catch (Exception e) {}
        cantidadSubidoTren++; 
        System.out.println("EL visitante "+Thread.currentThread().getName()+" se subió al tren, cantidad de personas subidas al tren; "+cantidadSubidoTren);
        this.notifyAll();
}


//metodo del control del tren

public synchronized void arrancarTren(){
    try {
    while(cantidadSubidoTren < cantidadTren){
        System.out.println("control espera");
        this.wait();
    }

  
    } catch (Exception e) {}
    System.out.println("ARRANCÓ EL TREN");
   
}


public synchronized void finalizarRecorridoTren(){
        System.out.println("EL TREN LLEGÓ A LA CARRERA DE GOMONES");
        cantidadSubidoTren = 0; 
        this.notifyAll();
}


//Visitantes que eligen bicicletas

public void subirBici(){
    try {
        semBicis.acquire();
        System.out.println("El visitante "+Thread.currentThread().getName()+" tomó una bici");
    } catch (InterruptedException e) { }

}

public void dejarBici(){
    System.out.println("El visitante llegó a la carreras de gomones con bicicleta");
    semBicis.release();
}

public void subirGomonSimple(){

}

public void subirGomonDoble(){
    
}

}
