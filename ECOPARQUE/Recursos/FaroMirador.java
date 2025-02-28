package Recursos;
import java.util.concurrent.Semaphore;

public class FaroMirador {

    public static final String CYAN = "\u001B[36m";  //colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m";  //colores para la salida por pantalla (mas legible)

    private boolean liberado1 = true;
    private boolean liberado2= true;
    private boolean pasaPor1 =true;
    Tiempo t; 

    //semaforos de un unico permiso
    private Semaphore control = new Semaphore(0);

    private Semaphore  escalera = new Semaphore(5); // n lugares
    
    private Semaphore pasaVisitante = new Semaphore(0);
    
    private Semaphore mutexToboganes = new Semaphore(2);
    
    private Semaphore tobogan1 = new Semaphore(1);
    private Semaphore tobogan2 = new Semaphore(1);


    public FaroMirador(Tiempo time){
        this.t = time; 
    }

    
    public void ingresar(){ 
        try {
            escalera.acquire();
            Thread.sleep(300);
            escalera.release();

        } catch (InterruptedException e) { e.printStackTrace();}
    }


    public void avisaControl(){
        if (t.permisoRealizarActividad()) {
            control.release();
        }
    }

    public boolean esperarTurno() throws InterruptedException {
        
        if (t.permisoRealizarActividad()) {
            System.out.println(CYAN+"°°° FARO MIRADOR °°° \n"+"El visitante " + Thread.currentThread().getName() + " espera el turno para usar tobogan"+RESET);
            pasaVisitante.acquire();// si no es turno del visitante, se queda bloqueado y espera  
        }
        return pasaPor1;
    }

    public void subirTobogan(boolean opcion1) throws InterruptedException { // de visitante
        if (t.permisoRealizarActividad()) { 
            mutexToboganes.acquire();
            if (opcion1) {  //opcion para subir a tobogán 1
                //System.out.println("El visitante " + Thread.currentThread().getName() + "deberia intentar subir al 1 ");
                //pasaVisitante.acquire(); // se bloquea esperando a que control lo deje pasar
                
                liberado1 = false; // si esta en el 1 es por que estaba libre
                tobogan1.acquire();
                System.out.println(CYAN+"°°° FARO MIRADOR °°° \n"+"El visitante " + Thread.currentThread().getName() + " se subió al tobogan 1, liberado1: "+ liberado1+ " liberado2: "+liberado2+RESET);
                //mutexToboganes.release();
                
                Thread.sleep(600);
                
                liberado1 = true;
                tobogan1.release();
                System.out.println(CYAN+"°°° FARO MIRADOR °°° \n"+"El visitante " + Thread.currentThread().getName() + " se bajó del tobogan 1, liberado1: "+ liberado1+ " liberado2: "+liberado2+RESET);
            
            } else { //opcion para subir a tobogán 2
                //System.out.println("El visitante " + Thread.currentThread().getName() + "deberia intentar subir al 2 ");
                //pasaVisitante.acquire(); // se bloquea esperando a que control lo deje pasar
    
                liberado2 = false;
                tobogan2.acquire();
                System.out.println(CYAN+"°°° FARO MIRADOR °°° \n"+"El visitante " + Thread.currentThread().getName() + "se subió al tobogan 2, liberado1: "+ liberado1+ " liberado2: "+liberado2+RESET);
                
                Thread.sleep(600);
                liberado2 = true;
                tobogan2.release();
                System.out.println(CYAN+"°°° FARO MIRADOR °°° \n"+"El visitante " + Thread.currentThread().getName() + " se bajó del tobogan 2, liberado1: "+ liberado1+ " liberado2: "+liberado2+RESET);
            }
            mutexToboganes.release();   
        }
    }

    public boolean seleccionarTobogan() throws InterruptedException { // del control
    //arranca con 2 permisos al principio para que pasen las primeras 2 personas
            control.acquire(); 
            if (t.permisoRealizarActividad()) {
                mutexToboganes.acquire();
                System.out.println(CYAN+"°°° FARO MIRADOR °°° \n"+"liberado1: "+ liberado1+ " liberado2: "+liberado2+RESET);
                if (liberado1) { //solo pasa si alguien se bajo
                    pasaPor1=true;
                } else if (liberado2) {
                    pasaPor1=false;
                }
                mutexToboganes.release();   
            }
        return pasaPor1;
    }

    public void avisarVisitante(){
            pasaVisitante.release();// deja que pase el visitante
    }

    
}
