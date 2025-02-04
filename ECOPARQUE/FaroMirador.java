import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
public class FaroMirador {
    private boolean liberado1 = true;
    private boolean liberado2= true;

    //semaforos de un unico permiso
    private Semaphore control = new Semaphore(0);

    private Semaphore  escalera = new Semaphore(5); // n lugares

    private Lock toboganes = new ReentrantLock();
    private Condition fila= toboganes.newCondition();

    public FaroMirador(){}

    public void ingresar(){
        try {
            escalera.acquire();
            Thread.sleep(2000);
            escalera.release();

        } catch (InterruptedException e) { e.printStackTrace();}
        //si lo tomo, y no se bloquea ahi entonces espera a que el control le de la señal
    }

    public void subirTobogan(){
        toboganes.lock();
        
        try {
           while(!liberado1 || !liberado2){
                fila.await();
           }
           
           if(){}

//nose como hacer q no se bloquee segun la decicion, capaz era mejor antes con semaforos

        } catch (InterruptedException e) { e.printStackTrace();}



        toboganes.unlock();
        }

        public void seleccionarTobogan(){
            toboganes.lock();
        }






}
