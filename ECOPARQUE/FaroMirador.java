import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
public class FaroMirador {

    private boolean liberado1 = true;
    private boolean liberado2= true;
    private boolean pasaPor1 =true;

    //semaforos de un unico permiso
    private Semaphore control = new Semaphore(2);

    private Semaphore  escalera = new Semaphore(5); // n lugares
    private Semaphore pasaVisitante = new Semaphore(0);
    private Semaphore esperaTurno = new Semaphore(0,true);//arranca esperando, y se otorgan en orden
    private Semaphore toboganes = new Semaphore(1);
    private Semaphore tobogan1 = new Semaphore(1);
    private Semaphore tobogan2 = new Semaphore(1);

/*  private Lock toboganes = new ReentrantLock();
    private Condition fila= toboganes.newCondition();
*/
    public FaroMirador(){}

    public void ingresar(){ // de visitante
        try {
            escalera.acquire();
            Thread.sleep(6000);
            escalera.release();

        } catch (InterruptedException e) { e.printStackTrace();}
        //si lo tomo, y no se bloquea ahi entonces espera a que el control le de la señal
    }

    public boolean esperarTurno() throws InterruptedException {

        System.out.println("El visitante " + Thread.currentThread().getName() + " espera el turno");
        pasaVisitante.acquire();// si no le toca pasar, se queda bloqueado y espera aca
        System.out.println("dejo de esperar wacho");

        //System.out.println("devuelvo que pase por 1?:" + pasaPor1);
        System.out.println("(VISITANTE)- le aviso al control que quiero pasar");
        control.release();
        return pasaPor1;
    }

    public void subirTobogan1() throws InterruptedException { // de visitante  
        System.out.println("El visitante "+Thread.currentThread().getName()+"deberia intentar subir al 1 ");
            pasaVisitante.acquire(); // se bloquea esperando a que control lo deje pasar
            //toboganes.acquire();
            System.out.println("El visitante "+Thread.currentThread().getName()+"trata de subir a tobogan 1 ");
           
            liberado1=false; // si esta en el 1 es por que estaba libre
            tobogan1.acquire();
            
        //toboganes.release();
    }

    public void bajarTobogan1(){
        liberado1=true;
            tobogan1.release();
            //control.release();
    }
   
    public void subirTobogan2() throws InterruptedException { // de visitante
        System.out.println("El visitante "+Thread.currentThread().getName()+"deberia intentar subir al 1 ");
            
            pasaVisitante.acquire(); // se bloquea esperando a que control lo deje pasar
            System.out.println("El visitante "+Thread.currentThread().getName()+"trata de subir a tobogan 2");
            liberado2 = false;
            tobogan2.acquire();
    
            System.out.println("soy: "+Thread.currentThread().getName()+"ME SUBI AL 2 ");
    }

    public void bajarTobogan2() {
        liberado2=true;
        tobogan2.release();
        //control.release();
    }

    public boolean seleccionarTobogan() throws InterruptedException { // del control
    //arranca con 2 permisos al princiio para que pasen las primeras 2 personas
            control.acquire(); 
            System.out.println("(CONTROL)- controlandooo"); 
            System.out.println("(CONTROL)- liberado1: "+ liberado1+ " liberado2: "+liberado2);
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
            pasaVisitante.release();// deja que pase el visitante
    }

    
}
