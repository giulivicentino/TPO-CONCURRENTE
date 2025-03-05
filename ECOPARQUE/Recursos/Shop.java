package Recursos;

import java.util.concurrent.Semaphore;

public class Shop {
    private Semaphore cajas = new Semaphore(2, true); // para que se respete el orden que quisieron acceder a una caja
    private Semaphore clientesEsperando = new Semaphore(0);
    private Semaphore esperaPagar = new Semaphore(0);
    public static final String RESET = "\u001B[0m";
    public static final String NARANJA = "\u001B[38;5;214m";

    public void comprar() throws InterruptedException { // metodo del visitante
        cajas.acquire();
        System.out.println(NARANJA + ".... SHOP .... \\n" + Thread.currentThread().getName() + " compra souvenir." + RESET);
        cajas.release();

    }

    public void esperaPagar() throws InterruptedException {
        clientesEsperando.release(); // avisa al cajero
        esperaPagar.acquire();
        System.out.println( NARANJA + ".... SHOP .... \\n" + Thread.currentThread().getName() + " le estan cobrando." + RESET);
    }

    public void irseTienda() {
        cajas.release();
        System.out.println(  NARANJA + ".... SHOP .... \\n" + Thread.currentThread().getName() + " se va de la tienda." + RESET);
    }

    public void atender() throws InterruptedException { // metodo de cajero
        clientesEsperando.acquire();
        Thread.sleep(1000); // le cobra
        esperaPagar.release(); // avisa a cliente
        System.out.println("le cobrorooo");
    }
}