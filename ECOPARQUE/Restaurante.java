import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurante {
    public static final String RESET = "\u001B[0m";  //colores para la salida por pantalla (mas legible)
    public static final String ROJO = "\u001B[31m";  //colores para la salida por pantalla (mas legible)

    private int cantActualResto = 0;
    private int id;
    private int capacidad;
    private boolean pedidoEnProceso = false;
    private HashMap<Persona, boolean[]> personas = new HashMap<>(); // <persona1, [almorzó,merendó]>
    Tiempo t;

    public Restaurante(int unId, int cantMax, Tiempo time) {
        id = unId;
        capacidad = cantMax;
        t = time;
    }

    private Lock lockResto = new ReentrantLock(); 
    private Condition fila = lockResto.newCondition();

    private Lock lockPedidos = new ReentrantLock();
    private Condition filaPedidos = lockPedidos.newCondition();

    public void entrarRestaurante(Persona personita) {
        lockResto.lock();
        if (t.permisoRealizarActividad()) {
            if (!personas.containsKey(personita)) {
                personas.put(personita, new boolean[] { false, false }); // agrega persona a la fila de ese restaurante
            }
            try {
                while (cantActualResto + 1 > capacidad) { // si al entrar la persona el restaurante  está lleno, espera
                    fila.await();
                }
                cantActualResto++;
                System.out.println(ROJO+".... RESTAURANTE .... \n"+ Thread.currentThread().getName() + " INGRESÓ a RESTAURANTE -" + id
                        + "- , cantActual: " + cantActualResto + " max: " + capacidad+ RESET);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lockResto.unlock();
    }

    public void pedirAlmuerzo(Persona personita) {

        lockPedidos.lock();
        if (t.permisoRealizarActividad()) {
            if (!personas.get(personita)[0]) { // verifica si la persona ya almorzó
                try {
                    while (pedidoEnProceso) {
                        filaPedidos.await();
                    }

                    System.out.println(ROJO+".... RESTAURANTE .... \n"+
                            Thread.currentThread().getName() + " se encuentra almorzando en: " + id+RESET);
                    Thread.sleep(200);
                    pedidoEnProceso = true;
                    personas.get(personita)[0] = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println(ROJO+".... RESTAURANTE .... \n"+Thread.currentThread().getName() + " ya almorzó, no puede repetir"+RESET);
            }
            pedidoEnProceso = false;
        }
        lockPedidos.unlock();

    }

    public void pedirMerienda(Persona personita) {
        lockPedidos.lock();
        if (t.permisoRealizarActividad()) {
            if (!personas.get(personita)[1]) { // verifica si la persona ya merendó
                try {
                    while (pedidoEnProceso) {
                        filaPedidos.await();
                    }

                    System.out.println(ROJO+".... RESTAURANTE .... \n"+Thread.currentThread().getName() + " merendando ------- EN: " + id+RESET);
                    Thread.sleep(200);
                    pedidoEnProceso = true;
                    personas.get(personita)[1] = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } 
            pedidoEnProceso = false;
        }

        lockPedidos.unlock();

    }

    public void salirRestaurante() {
        // metodo que modifica la cantidad actual de restaurante
        lockResto.lock();
        if (t.permisoRealizarActividad()) {
            cantActualResto--;
            System.out.println(ROJO+".... RESTAURANTE .... \n"+Thread.currentThread().getName() + " ME FUI de RESTO -" + id + "- , cantActual: "
                    + cantActualResto + " max: " + capacidad+RESET);
            fila.signalAll();
        }
        lockResto.unlock();
    }

}
