import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurante {
    private int cantActualResto = 0;
    private int id;
    private int capacidad;

    private  HashMap<Integer,int[]> restaurantes = new HashMap<>();
    private  HashMap<Persona,boolean[]> personas = new HashMap<>();
    


    public Restaurante() {
        restaurantes.put(1  , new int[]{0,10}); //resto 1 , tiene capacidad 10
        restaurantes.put(2  , new int[]{0,15});
        restaurantes.put(3  , new int[]{0,20});
    }


    private Lock lockResto = new ReentrantLock(); // el q se asegure que no haya mas de un hilo tratando de entrar a la vez, como un syncronised
    private Condition fila = lockResto.newCondition();
    
    private Lock lockPedidos = new ReentrantLock(); 
    private Condition filaPedidos = lockPedidos.newCondition();
    // nose si faltan mas condiciones

    public void entrarRestaurante(Persona personita){ 
      lockResto.lock();
      
        try {
            while( cantActualResto+1 > capacidad){ // si al entrar se llena
                fila.await();
            }
            cantActualResto++;
            System.out.println(Thread.currentThread().getName() + " entre a RESTO -"+id+"- , ahora la cant actual es: "+cantActualResto+ " y el max es "+capacidad); 
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
    }

    public void pedirAlmuerzo(Persona personita){
//sigue con el lock de cuando entró
        if(!personas.get(personita)[0]){

        }else{
            System.out.println(Thread.currentThread().getName() +" ya almorzo, no puede repetir"); 
        }

        cantActualResto--;
        fila.signalAll();
        lockResto.unlock();
    }

    public void pedirMerienda(Persona personita){

        personas.get(personita)[1] = true; // tacha la merienda
        lockResto.unlock();
    }


}



 /*  // creo q ya es muy rebuscado era para que se manden al que menos gente tenga
        int dif1y2= Math.abs(restaurantes.get(1)[0] - restaurantes.get(2)[0]) ; // resta actuales de resto 1 con los de resto2
        int dif1y3= Math.abs(restaurantes.get(1)[0] - restaurantes.get(2)[0]);
        int dif2y3= Math.abs(restaurantes.get(1)[0] - restaurantes.get(2)[0]);
        
        lockResto.lock();

        System.out.println(Thread.currentThread().getName() + " --- buscando restoo");
        if(restaurantes.get(1)[0]<10){ //se fija si en el primero puede
            System.out.println(Thread.currentThread().getName() + " entre a RESTO 1, ahora la cant actual es: "+restaurantes.get(1)[0]+ " y el max es 10");
        
        }else if(restaurantes.get(2)[0]<15){ // se fija en el segundo
            System.out.println(Thread.currentThread().getName() + " entre a RESTO 2, ahora la cant actual es: "+restaurantes.get(1)[0]+ " y el max es 10");
        
        }else if(restaurantes.get(3)[0]<20){ //sino se queda esperando en el tercero
            System.out.println(Thread.currentThread().getName() + " entre a RESTO 3, ahora la cant actual es: "+restaurantes.get(1)[0]+ " y el max es 10");
        
        }else{// que se quede esperando en el ultimo

        }
    */