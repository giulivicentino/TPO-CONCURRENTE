import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurante {
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

    private Lock lockResto = new ReentrantLock(); // el q se asegura que no haya mas de un hilo tratando de entrar a la
                                                  // vez, como un syncronised
    private Condition fila = lockResto.newCondition();

    private Lock lockPedidos = new ReentrantLock();
    private Condition filaPedidos = lockPedidos.newCondition();
    // nose si faltan mas condiciones

    public void entrarRestaurante(Persona personita) {
        lockResto.lock();
        if (t.verificarHora()) {
            if (!personas.containsKey(personita)) {
                personas.put(personita, new boolean[] { false, false }); // lo agrega a la fila de ese restaurante
            }
            try {
                while (cantActualResto + 1 > capacidad) { // si al entrar se llena
                    fila.await();
                }
                cantActualResto++;
                System.out.println(Thread.currentThread().getName() + " INGRESÓ a RESTAURANTE -" + id
                        + "- , cantActual: " + cantActualResto + " max: " + capacidad);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(Thread.currentThread().getName() + " se retiró del RESTAURANTE, se acabó el tiempo");
        }

        lockResto.unlock();
    }

    public void pedirAlmuerzo(Persona personita) {

        lockPedidos.lock();
        if (t.verificarHora()) {
            if (!personas.get(personita)[0]) { // que no haya almorzado
                try {
                    while (pedidoEnProceso) {
                        filaPedidos.await();
                    }

                    System.out.println(
                            Thread.currentThread().getName() + " ............se encuentra almorzando en: " + id);
                    Thread.sleep(200);
                    pedidoEnProceso = true;
                    personas.get(personita)[0] = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println(Thread.currentThread().getName() + " ya almorzó, no puede repetir");
            }
            pedidoEnProceso = false;
        }
        lockPedidos.unlock();

    }

    public void pedirMerienda(Persona personita) {
        lockPedidos.lock();
        if (t.verificarHora()) {
            if (!personas.get(personita)[1]) { // que no haya merendado
                try {
                    while (pedidoEnProceso) {
                        filaPedidos.await();
                    }

                    System.out.println(Thread.currentThread().getName() + " merendando ------- EN: " + id);
                    Thread.sleep(200);
                    pedidoEnProceso = true;
                    personas.get(personita)[1] = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                // System.out.println(Thread.currentThread().getName() +" ya merendo, no puede
                // repetir");
            }
            pedidoEnProceso = false;
        }

        lockPedidos.unlock();

    }

    public void salirRestaurante() {
        // para modificar la cant actual
        lockResto.lock();
        if (t.verificarHora()) {
            cantActualResto--;
            System.out.println(Thread.currentThread().getName() + " ME FUI de RESTO -" + id + "- , cantActual: "
                    + cantActualResto + " max: " + capacidad);
            fila.signalAll();
        }
        lockResto.unlock();
    }

}
