import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
public class FaroMirador {
    private boolean liberado1 =true;
    private boolean liberado2 =true;
    private boolean pasaPor1 =true;

    //semaforos de un unico permiso
    private Semaphore control = new Semaphore(0 );
    
    private Semaphore  escalera = new Semaphore(5); // n lugares
   //private Semaphore pasaVisitante = new Semaphore;
    private Semaphore esperaTurno = new Semaphore(0,true);//arranca esperando, y se otorgan en orden
    private Semaphore toboganes = new Semaphore(1);
    private Semaphore tobogan1 = new Semaphore(1);
    private Semaphore tobogan2 = new Semaphore(1);

/*  private Lock toboganes = new ReentrantLock();
    private Condition fila= toboganes.newCondition();
*/
    public FaroMirador(){}

    public void ocuparEscalera() throws InterruptedException{ // de visitante
            escalera.acquire();//si lo tomo, y no se bloquea ahi entonces espera a que el control le de la señal
            Thread.sleep(6000);
           
    }
    public void desocuparEscalera(){
        escalera.release();
    }

    public void avisaControl(){
        control.release();
    }
    public boolean seleccionarTobogan() throws InterruptedException { // del control
        //pasa por que alguien le avisa que quiere entrar
            control.acquire(); 
            //System.out.println("(CONTROL)- controlandooo"); 
            //System.out.println("(CONTROL)- liberado1: "+ liberado1+ " liberado2: "+liberado2);
           
           //solo avanza si es que existe algun tobogan desocupado
            toboganes.acquire();//SOLO UN CONTROL   protege las variables de estado liberado1/liberado2
            if (liberado1) { //solo pasa si alguien se bajo
                pasaPor1=true;
                System.out.println("(CONTROL)- el 1 estaba liberado, lo mando ahi");
                
            } else if (liberado2) {
                pasaPor1=false;
                System.out.println("(CONTROL)- el 2 estaba liberado, lo madno ahi");
            }
            
        return pasaPor1;
    }

    public void avisarVisitante(){
         System.out.println("(CONTROL)-le chiflo a un visitante que pase");
            esperaTurno.release();// deja que pase el visitante
    }

    public boolean esperarTurno() throws InterruptedException {
        
        //toboganes.acquire();
        System.out.println("(VISITANTE)- le aviso al control que quiero pasar");
        control.release();
        Thread.sleep(200);
        //toboganes.release();
        System.out.println("El visitante " + Thread.currentThread().getName() + " espera el turno");
        esperaTurno.acquire();// si no le toca pasar, se queda bloqueado y espera aca
        System.out.println("dejo de esperar wacho");

        // System.out.println("devuelvo que pase por 1?:" + pasaPor1);

        return pasaPor1;
    }

    public void subirTobogan1(boolean pasa1) throws InterruptedException { // de visitante
        // System.out.println("El visitante "+Thread.currentThread().getName()+"deberia
        // intentar subir al 1 ");
        esperaTurno.acquire(); // se bloquea esperando a que control lo deje pasar
        toboganes.acquire();
        if (pasa1) {
            System.out.println("El visitante " + Thread.currentThread().getName() + "trata de subir a tobogan 1");
            liberado1 = false; // si esta en el 1 es por que estaba libre
            tobogan1.acquire();
            System.out.println("----------SUBOOOOO " + Thread.currentThread().getName() + " SUBE a tobogan 1 ");
        } else {
            // System.out.println("El visitante "+Thread.currentThread().getName()+"deberia
            // intentar subir al 1 ");

            System.out.println("El visitante " + Thread.currentThread().getName() + "trata de subir a tobogan 2");
            liberado2 = false;
            tobogan2.acquire();

            System.out.println("-------SUBOOO " + Thread.currentThread().getName() + "ME SUBI AL 2 ");
        }

        toboganes.release();
    }

    public void bajarTobogan1(boolean pasa1) {
        
        if (pasa1) {
            liberado1 = true;
            tobogan1.release();
            System.out.println("...........BAJOOOOOOO  " + Thread.currentThread().getName() + "SE BAJA de tobogan 1 ");
            // control.release();
        } else {
            liberado2 = true;
            tobogan2.release();
            System.out.println("........BAJOOOOO  " + Thread.currentThread().getName() + "SE BAJA de tobogan 1 ");
        }

    }
   

   
    
}
