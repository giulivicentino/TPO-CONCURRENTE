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

        Random r1 = new Random();//2
        int eleccionResto= r1.nextInt(0,3);
        
        Random r3 = new Random();//3
        int ladoTirolesa = r3.nextInt(1,2);
        
        Random r4 = new Random();// 4
        int eleccionTransporte= r4.nextInt(1,2);

        Random r5 = new Random();//4
        int eleccionGomon= r5.nextInt(1,2);

        try {
            parque.realizarActividad(1, this,eleccionResto,ladoTirolesa,eleccionTransporte,eleccionGomon);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}