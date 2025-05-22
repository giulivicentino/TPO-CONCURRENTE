package Hilos;

import Recursos.Colectivo;
import Recursos.Tiempo;

public class Colectivero extends Thread {
    private Colectivo cole;
  private Tiempo t;
    public Colectivero(Colectivo c,Tiempo t) {
        this.cole = c;
        this.t=t;
    }

    public void run() {
        try {
            while (t.verificarIngreso()) {
               
                Thread.sleep(4500); // cada 15 minutos pasa un cole
                 cole.arrancarCole();
                Thread.sleep(3000); //10 minutos de viaje de ida al parque
                
                cole.terminaViaje();
                Thread.sleep(3000); //10 minutos de viaje de vuelta a la parada
                cole.vueltaCole();
                
            }
        } catch (Exception e) {
        }
    }
}