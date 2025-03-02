package Hilos;

import java.util.Random;
import Recursos.Colectivo;
import Recursos.Parque;
import Recursos.Shop;
import Recursos.Tiempo;

public class Persona extends Thread {

    private Parque parque; // contiene todas las actividades
    private Colectivo cole;
    private Tiempo tiempo;
    private Shop tienda;

    public Persona(String n, Colectivo cole, Parque unParque, Tiempo t, Shop tiendita) {
        this.setName(n);
        this.cole = cole;
        this.parque = unParque;
        this.tiempo = t;
        this.tienda = tiendita;
    }

    public void run() {
        Random r = new Random();
        boolean porCole = r.nextBoolean();

        if (porCole) {
            System.out.println(Thread.currentThread().getName().toString()
                    + " se dirige a la fila del colectivo");
            try {
                System.out.println(Thread.currentThread().getName().toString()
                        + " SUBIO al colectivo");
                boolean cole1 = cole.subirCole();
                Thread.sleep(3000);
                cole.bajarCole(cole1);
                if (tiempo.verificarIngreso()) {
                    System.out.println(Thread.currentThread().getName().toString()
                            + " llega a destino con el tour");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (tiempo.verificarIngreso()) {
                System.out.println(Thread.currentThread().getName().toString() + " accede de manera particular");
            }
        }

        Random r7 = new Random();
        boolean pasaPorShop = r7.nextBoolean();

        if (pasaPorShop) {
            try {
                tienda.comprar();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // se cambia el eleccion aleatorio hasta que un numero salga (6) que determine
        // que ya se quiere ir
        /*
         * if (tiempo.verificarIngreso()) { // Verifica si puede ingresar al parque
         * Random r6 = new Random();// 4
         * int eleccionActividad = r6.nextInt(6);
         * int eleccionResto = 0, ladoTirolesa = 0, eleccionGomon = 0,
         * eleccionTransporte = 0; // si se nesesita
         * // alguno, se establece
         * // aleatorio solo ese
         * // atributo
         * switch (eleccionActividad) { // para que solo genere el atributo aleatorio
         * que le corresponda
         * case 2:
         * Random r1 = new Random();// 2
         * eleccionResto = r1.nextInt(3) + 1;
         * break;
         * case 3:
         * Random r3 = new Random();// 3
         * ladoTirolesa = r3.nextInt(2) + 1;
         * break;
         * case 4:
         * Random r4 = new Random();// 4
         * eleccionTransporte = r4.nextInt(2) + 1;
         * 
         * Random r5 = new Random();// 4
         * eleccionGomon = r5.nextInt(2) + 1;
         * break;
         * }
         * 
         * while (tiempo.verificarHora()) { // Verifica si puede realizar alguna
         * actividad
         * 
         * try {
         * parque.realizarActividad(eleccionActividad, this, eleccionResto,
         * ladoTirolesa, eleccionTransporte,
         * eleccionGomon);
         * } catch (InterruptedException e) {
         * e.printStackTrace();
         * }
         * eleccionActividad = r6.nextInt(6);
         * }
         * 
         * }
         */

        Random r8 = new Random();
        boolean pasaPorShopV = r8.nextBoolean();
        System.out.println(Thread.currentThread().getName().toString() + "SUPOOONETEEEEE????????   " + pasaPorShopV);

        if (pasaPorShopV) {
            try {
                tienda.comprar();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        System.out.println(Thread.currentThread().getName().toString() + "se va del parque");
    }
}