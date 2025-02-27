import java.util.concurrent.Semaphore;

public class Laguna { // recurso compartido

    Semaphore equipo = new Semaphore(10); // cantidad de equipos disponibles
    Semaphore verificarEquipo = new Semaphore(0);
    Semaphore equipoOtorgado = new Semaphore(0);
    Tiempo t;

    public Laguna(Tiempo time) {
        this.t = time;
    }

    public void solicitarEquipo() {
        if(t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 45)){
            verificarEquipo.release();
        }
    }

    public void otorgarEquipo() throws InterruptedException {
            verificarEquipo.acquire();
        if (t.getHora() < 17 || (t.getHora() == 17 && t.getMinuto() < 50)) {
            equipo.acquire();
            System.out.println("/// LAGUNA /// \n"+"equipo otorgado");
            equipoOtorgado.release();
        }
    }

    public void devolverEquipo() throws InterruptedException {
        if (t.verificarHora()) {
            equipoOtorgado.acquire();
            System.out.println("/// LAGUNA /// \n"+"La persona " + Thread.currentThread().getName() + " terminó de usar la laguna");
            equipo.release();
        }
    }

}