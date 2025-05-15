package Hilos;

import Recursos.CarreraGomones;

public class ControlTren extends Thread {   //Hilo control tren utilizado en la carrera de gomones
    private CarreraGomones cg;
    private boolean permiso;

    public ControlTren(CarreraGomones c) {
        this.cg = c;
    }

    public void run() {
        try {
            while (true) {
                permiso = cg.arrancarTren();
                Thread.sleep(1500);
                cg.finalizarRecorridoTren(permiso);
                Thread.sleep(1500);
                cg.vueltaTren(permiso);
            }
        } catch (Exception e) {
        }
    }
}