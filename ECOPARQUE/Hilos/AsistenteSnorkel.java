package Hilos;

import Recursos.Laguna;

public class AsistenteSnorkel  extends Thread{
    private Laguna laguna; 
    
    public AsistenteSnorkel(Laguna l) {
        laguna = l; 
    }

    public void run(){
        while(true){
            try {
                laguna.otorgarEquipo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
        }
    }

}
