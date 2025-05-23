package Hilos;

import Recursos.NadoDelfines;
import Recursos.Tiempo;

public class ControlTiempo extends Thread {
    private Tiempo tiempo;
    private NadoDelfines nd;

    public ControlTiempo(Tiempo t, NadoDelfines nd) {
        this.tiempo = t;
        this.nd = nd;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(300); // simulacion de tiempo para un minuto
                tiempo.aumentarMinuto();

                // Control del tiempo para las funciones de nado de delfines
                if (tiempo.getMinuto() == 15) {
                    nd.avisaControlComienzo(); // 15 minutos de espera para inicio de funcion
                } else if (tiempo.getMinuto() == 0) {
                    nd.avisaControlFin();
                }

            }
        } catch (Exception e) {
        }

    }
}
