package Recursos;

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
        if (t.permisoRealizarActividad()) { // 15 minutos antes del cierre es el tiempo limite para utilizar la laguna
            verificarEquipo.release(); // cliente solicita equipo
            try {
                Thread.sleep(500);
                devolverEquipo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void otorgarEquipo() throws InterruptedException {
        verificarEquipo.acquire(); // asistente verifica que haya equipo para dar a cliente
        if (t.permisoRealizarActividad()) {
            equipo.acquire();
            System.out.println("/// LAGUNA /// \n" + "equipo otorgado");
            equipoOtorgado.release();
        } else {
            equipoOtorgado.release();
        }
    }

    public void devolverEquipo() throws InterruptedException {
        equipoOtorgado.acquire();
        if (t.permisoRealizarActividad()) {
            System.out.println(
                    "/// LAGUNA /// \n" + "La persona " + Thread.currentThread().getName()
                            + " terminó de usar la laguna");
            equipo.release(); // cliente devuelve equipo
        } else {
            equipoOtorgado.release();
        }

    }

}