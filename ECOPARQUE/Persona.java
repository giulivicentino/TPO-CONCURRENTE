import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Persona extends Thread {
    
     private Parque parque; // contiene todas las actividades
     private Colectivo cole;


    public Persona(String n,Colectivo cole,Parque unParque) {
        this.setName(n);
        this.cole=cole;
        this.parque = unParque;
            }

    public void run() {
        Random r = new Random();
        boolean porCole = r.nextBoolean();

        if(porCole){
            System.out.println("La persona: "+Thread.currentThread().getName().toString()+" se dirige a la fila del colectivo");
            try {
                System.out.println("La persona: "+Thread.currentThread().getName().toString()+" SUBIO al colectivo");
                boolean cole1=cole.subirCole();
                Thread.sleep(3000);
                cole.bajarCole(cole1);
                System.out.println("La persona: "+Thread.currentThread().getName().toString()+" llega a destino con el tour");
            } catch (InterruptedException e) { e.printStackTrace();} 
        }else{
            System.out.println("La persona: "+Thread.currentThread().getName().toString()+" accede de manera particular");
        }
        
        // se cambia el eleccion aleatorio hasta que un numero salga (6) que determine que ya se quiere ir
        try {
            parque.realizarActividad(1, this);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}