package Hilos;

import Recursos.MundoAventura;

public class ControlTirolesa extends Thread { // Hilo control de tirolesa utilizada en Mundo Aventura
    private MundoAventura ma;
    private char lado;

    public ControlTirolesa(MundoAventura m, char lado) {
        this.ma = m;
        this.lado = lado;
    }

    public void run() {
        while (true) {
            switch (lado) {
                case 'e':
                    ma.verficarTirolesaEste();
                    break;

                case 'o':
                    ma.verficarTirolesaOeste();
                    break;
            }
        }
    }

}