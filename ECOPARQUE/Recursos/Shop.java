package Recursos;

import java.util.concurrent.Semaphore;

public class Shop {
    private Semaphore cajas = new Semaphore(2, true); // para que se respete el orden que quisieron acceder a una caja
    private Semaphore clientesEsperando = new Semaphore(0);
    private Semaphore esperaPagar = new Semaphore(0);
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)
    public static final String NARANJA = "\u001B[38;5;214m"; // Naranja en la paleta de 256 colores

    public void comprar() throws InterruptedException {
        Thread.sleep(1000);
        cajas.acquire();
        System.out.println(
                NARANJA + ".... SHOP .... \\n" + Thread.currentThread().getName() + " compra souvenir." + RESET);
        clientesEsperando.release();
        System.out.println(
                NARANJA + ".... SHOP .... \\n" + Thread.currentThread().getName() + " le estan cobrando." + RESET);
        esperaPagar.acquire();
        System.out.println(
                NARANJA + ".... SHOP .... \\n" + Thread.currentThread().getName() + " se va de la tienda." + RESET);
        cajas.release();
    }

    public void atender() throws InterruptedException {
        clientesEsperando.acquire();
        Thread.sleep(1000);

        esperaPagar.release();
    }
}