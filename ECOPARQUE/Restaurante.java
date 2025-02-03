import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurante {
    private int cantActualResto = 0;
    private int id;
    private int capacidad;
    private boolean pedidoEnProceso =false;
    private  HashMap<Persona,boolean[]> personas = new HashMap<>(); // <persona1, [almorzo,merendo]>
    
    public Restaurante(int unId,int cantMax) {
       id=unId;
       capacidad=cantMax;
    }

    private Lock lockResto = new ReentrantLock(); // el q se asegure que no haya mas de un hilo tratando de entrar a la vez, como un syncronised
    private Condition fila = lockResto.newCondition();
    
    private Lock lockPedidos = new ReentrantLock(); 
    private Condition filaPedidos = lockPedidos.newCondition();
    // nose si faltan mas condiciones

    public void entrarRestaurante(Persona personita) {
        lockResto.lock();
        if (!personas.containsKey(personita)) {
            personas.put(personita, new boolean[] { false, false }); // lo agrega a la fila de ese restaurante
            try {
                while (cantActualResto + 1 > capacidad) { // si al entrar se llena
                    fila.await();
                }
                cantActualResto++;
                System.out.println(Thread.currentThread().getName() + " ENTRE a RESTO -" + id
                + "- , cantActual: " + cantActualResto + " max: " + capacidad);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        lockResto.unlock();
    }

    public void pedirAlmuerzo(Persona personita){
        lockPedidos.lock();
        if(!personas.get(personita)[0]){ //que no haya almorzado
            try {
                while (pedidoEnProceso) {
                    filaPedidos.await();
                }
            
              //  System.out.println(Thread.currentThread().getName() +" almorzando EN: "+id); 
                Thread.sleep(200);
                pedidoEnProceso=false;
                personas.get(personita)[0]=true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
           // System.out.println(Thread.currentThread().getName() +" ya almorzo, no puede repetir"); 
        }
        pedidoEnProceso=false;
        lockPedidos.unlock();
    }

    public void pedirMerienda(Persona personita){
        lockPedidos.lock();
        if(!personas.get(personita)[1]){ //que no haya merendado
            try {
                while (pedidoEnProceso) {
                    filaPedidos.await();
                }
            
                //System.out.println(Thread.currentThread().getName() +" merendando EN: "+id); 
                Thread.sleep(200);
                pedidoEnProceso=false;
                personas.get(personita)[1]=true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
           // System.out.println(Thread.currentThread().getName() +" ya merendo, no puede repetir"); 
        }
        pedidoEnProceso=false;
        lockPedidos.unlock();

        
    }

    public void salirRestaurante(){
        //para modificar la cant actual
        lockResto.lock();        
        cantActualResto--;
        System.out.println(Thread.currentThread().getName() + " ME FUI de RESTO -" + id
                        + "- , cantActual: " + cantActualResto + " max: " + capacidad);
        fila.signalAll(); 
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