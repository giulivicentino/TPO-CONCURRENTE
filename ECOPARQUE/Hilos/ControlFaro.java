package Hilos;

import Recursos.FaroMirador;

public class ControlFaro extends Thread{  //Hilo control faro utilizado en faro-mirador
    private FaroMirador fa; 
    
    public ControlFaro(FaroMirador farito){
        this.fa = farito; 
    }

    public void run(){
        while(true){
            try {
                fa.seleccionarTobogan();
                fa.avisarVisitante();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
