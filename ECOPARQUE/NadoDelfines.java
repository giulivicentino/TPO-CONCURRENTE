package ECOPARQUE;

import java.util.concurrent.Semaphore;

public class NadoDelfines {

    private int cambioPile = 1;
    private boolean ingreso = true;
    private int cantActualPile = 0;


    public synchronized void solPile() {
        try {
            while (!ingreso) {
                this.wait();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (cantActualPile < 10) {
            cantActualPile++;
            System.out.println("CANTIDAD PERSONAS PILETA " + cambioPile + ": " + cantActualPile);
        } else {
            switch (cambioPile) {
                case 1:
                    cambioPile = 2;
                    break;
                case 2:
                    cambioPile = 3;
                    break;
                case 3:
                    cambioPile = 4;
                    break;
                case 4:
                    cambioPile = 1;
                    break;
            }
            cantActualPile = 0;
            System.out.println("CAMBIO PILETA: " + cambioPile);
        }

        if(cambioPile == 4 && cantActualPile == 10){
            ingreso = false; 
        }

        this.notifyAll();
    }

    public synchronized void comenzarFuncion(){
        try {
            while(cambioPile!= 4){
                this.wait();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Comienza funcion");
        ingreso = false; 
    }

    public synchronized void terminarFuncion(){
        System.out.println("Funcion terminada");
        ingreso = true;  
        cambioPile = 1;
        cantActualPile = 0; 
        this.notifyAll(); 
    }
}

