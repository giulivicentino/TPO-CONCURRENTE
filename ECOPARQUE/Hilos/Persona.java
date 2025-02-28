package Hilos;

import java.util.Random;
import Recursos.Colectivo;
import Recursos.Parque;
import Recursos.Tiempo;


public class Persona extends Thread {

    private Parque parque; // contiene todas las actividades
    private Colectivo cole;
    private Tiempo tiempo;

    public Persona(String n, Colectivo cole, Parque unParque, Tiempo t) {
        this.setName(n);
        this.cole = cole;
        this.parque = unParque;
        this.tiempo = t;
    }

    public void run() {
        Random r = new Random();
        boolean porCole = r.nextBoolean();

        if (porCole) {
            System.out.println("La persona: " + Thread.currentThread().getName().toString()
                    + " se dirige a la fila del colectivo");
            try { 
                    System.out .println("La persona: " + Thread.currentThread().getName().toString()
                                    + " SUBIO al colectivo");
                    boolean cole1 = cole.subirCole();
                    Thread.sleep(3000);
                    cole.bajarCole(cole1);
                    if (tiempo.verificarIngreso()) {
                        System.out.println("La persona: " + Thread.currentThread().getName().toString()
                        + " llega a destino con el tour");   
                    }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (tiempo.verificarIngreso()) {
                System.out.println(
                    "La persona: " + Thread.currentThread().getName().toString() + " accede de manera particular");   
            }            
        }

        // se cambia el eleccion aleatorio hasta que un numero salga (6) que determine
        // que ya se quiere ir

        if (tiempo.verificarIngreso()) { // Verifica si puede ingresar al parque
            Random r1 = new Random();// 2
            int eleccionResto = r1.nextInt(3) + 1;

            Random r3 = new Random();// 3
            int ladoTirolesa = r3.nextInt(2) + 1;

            Random r4 = new Random();// 4
            int eleccionTransporte = r4.nextInt(2) + 1;

            Random r5 = new Random();// 4
            int eleccionGomon = r5.nextInt(2) + 1;

            Random r6 = new Random();// 4
            int eleccionActividad = r6.nextInt(6);

            while (tiempo.verificarHora()) { // Verifica si puede realizar alguna actividad

                try {
                    parque.realizarActividad(eleccionActividad, this, eleccionResto, ladoTirolesa, eleccionTransporte, eleccionGomon);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                eleccionActividad = r6.nextInt(6);
            }

        }
    }
}