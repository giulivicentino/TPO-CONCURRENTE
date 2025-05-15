package Hilos;

import Recursos.Shop;

public class Cajero extends Thread {
    private Shop tienda;
    private int id;
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)
    public static final String NARANJA = "\u001B[38;5;214m"; // Naranja en la paleta de 256 colores

    public Cajero(int id, Shop tiendita) {
        this.tienda = tiendita;
        this.id = id;
    }

    public void run() {
        System.out.println(NARANJA + ".... SHOP .... Cajero " + id + " esta listo" + RESET);
        while (true) {
            try {
                tienda.atender();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}