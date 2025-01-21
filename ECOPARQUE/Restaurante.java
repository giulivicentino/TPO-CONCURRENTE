import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurante {
    private int cantActualResto = 0;
    private int id;
    private int capacidad;

    public Restaurante(int id, int capacidad) {
        this.id = id;
        this.capacidad = capacidad;
    }

    private Lock resto = new ReentrantLock(); // el q se asegure que no haya mas de un hilo tratando de entrar a la vez,
                                              // como un syncronised
    private Condition persona = resto.newCondition();
    // nose si faltan mas condiciones

}
