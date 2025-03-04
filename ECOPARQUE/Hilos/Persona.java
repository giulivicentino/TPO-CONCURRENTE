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
                e.printStackTrace();
            }
        }

        // se cambia el eleccion aleatorio hasta que un numero salga (6) que determine
        // que ya se quiere ir

        if (tiempo.verificarIngreso()) { // Verifica si puede ingresar al parque
            Random r1 = new Random();
            Random r3 = new Random();
            Random r4 = new Random();
            Random r5 = new Random();
            Random r6 = new Random();

          //  int eleccionActividad = r6.nextInt(6);
          int eleccionActividad = 2; 
            int eleccionResto = 0, ladoTirolesa = 0, eleccionGomon = 0,
                    eleccionTransporte = 0;

            while (tiempo.verificarHora()) { // Verifica si puede realizar alguna actividad

                switch (eleccionActividad) { // estructura switch para que solo genere el atributo aleatorio que le
                                             // corresponda
                    case 2:
                        // numero random para la eleccion del restaurante
                        eleccionResto = r1.nextInt(3) + 1;
                        break;

                    case 3:
                        // numero random para elegir el lado de la tirolesa
                        ladoTirolesa = r3.nextInt(2) + 1;
                        break;

                    case 4:
                        // numero random para la eleccion del transporte
                        eleccionTransporte = r4.nextInt(2) + 1;

                        // numero random para la eleccion del tipo de gomon
                        eleccionGomon = r5.nextInt(2) + 1;
                        break;
                }

                try {
                    parque.realizarActividad(eleccionActividad, this, eleccionResto, ladoTirolesa, eleccionTransporte,
                            eleccionGomon);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

           //     eleccionActividad = r6.nextInt(6);
            }

        }

        System.out.println(Thread.currentThread().getName().toString() + "se va del parque");
    }
}