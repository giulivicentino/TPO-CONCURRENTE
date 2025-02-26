import java.util.concurrent.Semaphore;

public class Laguna { //recurso compartido 
    
    
    Semaphore equipo = new Semaphore(10); //cantidad de equipos disponibles 
    Semaphore verificarEquipo = new Semaphore(0);  
    Semaphore equipoOtorgado = new Semaphore(0); 
    Tiempo t; 
    
    public Laguna(Tiempo time){
        this.t = time;  
    }


    public void solicitarEquipo(){
        verificarEquipo.release();
    }

    public void otorgarEquipo() throws InterruptedException{
        verificarEquipo.acquire();
        if(t.verificarHora()){
            equipo.acquire();  
            System.out.println("equipo otorgado");
            equipoOtorgado.release();
        }
    }

    public void devolverEquipo() throws InterruptedException{
        equipoOtorgado.acquire();
        if(t.verificarHora()){
            System.out.println("La persona "+Thread.currentThread().getName()+" devuelve el equipo");
            equipo.release();
        }
    }
    

}