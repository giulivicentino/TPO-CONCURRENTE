package Recursos;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MundoAventura { // recurso compartido por tirolesas, cuerdas y saltos

    public static final String VERDE = "\u001B[32m";// colores para la salida por pantalla (mas legible)
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)

    private int cantSaltos;
    private int cuerda;
    private int cantEsperandoLado1;
    private int cantEsperandoLado2;
    private int tirolesaEste;
    private int tirolesaOeste;
    private boolean retornoEste;
    private boolean retornoOeste;
    private Lock accesoTirolesa;
    private Lock accesoCuerda;
    private Lock accesoSalto;
    private Condition colaTirolesa1; // representa el lado ESTE de la tirolesa
    private Condition colaTirolesa2; // representa el lado OESTE lado de la tirolesa
    private Condition controlEste;
    private Condition controlOeste;
    private Condition colaSalto; // cola de espera salto
    private Condition colaCuerda; // cola de espera cuerda
    private Tiempo t;

    public MundoAventura(Tiempo time) {
        this.cantSaltos = 2;
        this.cuerda = 1;
        this.accesoTirolesa = new ReentrantLock(true);
        this.accesoCuerda = new ReentrantLock(true);
        this.accesoSalto = new ReentrantLock(true);
        colaTirolesa1 = accesoTirolesa.newCondition();
        colaTirolesa2 = accesoTirolesa.newCondition();
        controlEste = accesoTirolesa.newCondition();
        controlOeste = accesoTirolesa.newCondition();
        colaCuerda = accesoCuerda.newCondition();
        colaSalto = accesoSalto.newCondition();
        this.t = time;
        tirolesaEste = 1;
        tirolesaOeste = 1;
        retornoEste = false;
        retornoOeste = false;
    }

    // TIROLESA

    public void subirTirolesa() { // subir a tirolesa del lado ESTE
        try {
            accesoTirolesa.lock();
            cantEsperandoLado1++;
            while (tirolesaEste == 0) {
                colaTirolesa1.await();
            }
            if (t.permisoRealizarActividad()) {
                tirolesaEste--;
                cantEsperandoLado1--;
                System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n"
                        + "La persona " + Thread.currentThread().getName()
                        + " se encuentra utilizando la tirolesa de ESTE a OESTE " + RESET);
            }
        } catch (InterruptedException e) {

        } finally {
            accesoTirolesa.unlock();
        }

    }

    public void subirTirolesa2() { // subir a tirolesa del lado OESTE
        try {
            accesoTirolesa.lock();
            cantEsperandoLado2++;
            while (tirolesaOeste == 0) {
                colaTirolesa2.await();
            }
            if (t.permisoRealizarActividad()) {
                tirolesaOeste--;
                cantEsperandoLado2--;
                System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n"
                        + "La persona " + Thread.currentThread().getName()
                        + " se encuentra utilizando la tirolesa de OESTE a ESTE" + RESET);
            }
        } catch (InterruptedException e) {

        } finally {
            accesoTirolesa.unlock();
        }

    }

    public void bajarseTirolesa() { // bajarse de la tirolesa en OESTE
        accesoTirolesa.lock();
        retornoEste = true;
        if (t.permisoRealizarActividad()) {
            tirolesaEste++;
            System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n"
                    + "La persona " + Thread.currentThread().getName()
                    + " se bajó de la tirolesa de ESTE a OESTE " + RESET);
            controlEste.signal();
        } else {
            controlEste.signal();
        }
        accesoTirolesa.unlock();
    }

    public void bajarseTirolesa2() { // bajarse de la tirolesa en ESTE
        accesoTirolesa.lock();
        retornoOeste = true;
        if (t.permisoRealizarActividad()) {
            tirolesaOeste++;
            System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n"
                    + "La persona " + Thread.currentThread().getName()
                    + " se bajó de la tirolesa de OESTE a ESTE " + RESET);
            controlOeste.signal();
        } else {
            controlOeste.signal();
        }
        accesoTirolesa.unlock();
    }

    public void verficarTirolesaEste() { // metodo del control de la tirolesa
        accesoTirolesa.lock();
        try {
            while (!retornoEste) {
                controlEste.wait();
            }
            retornoEste = false;
            if (t.permisoRealizarActividad()) { // verifica que se pueda seguir usando la tirolesa

                if (cantEsperandoLado2 > 0) { // verifica que haya gente esperando para subir a la tirolesa desde el
                                              // OESTE
                    colaTirolesa2.signal();
                } else {
                    // Si no hay nadie, avisa a control para mandar a tirolesa al lado ESTE
                    System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n"
                            + "LADO OESTE VACIO" + RESET);
                    System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n" + "TIROLESA VA DE OESTE A ESTE" + RESET);
                    Thread.sleep(200);

                    if (cantEsperandoLado1 > 0) { // consulta si hay gente esperando desde el Este
                        colaTirolesa1.signal();
                    }

                }
            } else {
                tirolesaEste++;
                if (cantEsperandoLado2 > 0) { // verifica que haya gente esperando
                    colaTirolesa2.signal();
                }
            }

        } catch (Exception e) {
        }
        accesoTirolesa.unlock();
    }

    public void verficarTirolesaOeste() { // metodo del control de la tirolesa
        accesoTirolesa.lock();
        try {
            while (!retornoOeste) {
                controlOeste.wait();
            }
            retornoOeste = false;
            if (t.permisoRealizarActividad()) {
                if (cantEsperandoLado1 > 0) { // verifica que haya gente esperando para subir a la tirolesa desde el
                                              // ESTE
                    colaTirolesa1.signal();
                } else {
                    // Si no hay nadie, avisa a control para mandar a tirolesa al lado ESTE
                    System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n"
                            + "LADO OESTE VACIO" + RESET);
                    System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n" + "TIROLESA VA DE OESTE A ESTE" + RESET);
                    Thread.sleep(200);

                    if (cantEsperandoLado2 > 0) { // consulta si hay geste esperando desde el Oeste
                        colaTirolesa2.signal();
                    }

                }
            } else {
                tirolesaOeste++;
                if (cantEsperandoLado1 > 0) { // verifica que haya gente esperando
                    colaTirolesa1.signal();
                }
            }

        } catch (Exception e) {
        }
        accesoTirolesa.unlock();
    }

    // CUERDA
    public void usarCuerda() {
        accesoCuerda.lock();
        try {
            while (cuerda < 1) {
                colaCuerda.await();
            }
            if (t.permisoRealizarActividad()) {
                cuerda--;
                System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n" + "La persona "
                        + Thread.currentThread().getName() + " se encuentra usando la cuerda" + RESET);
                Thread.sleep(900);
                System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n" + "La persona "
                        + Thread.currentThread().getName() + " dejó de usar la cuerda" + RESET);
                cuerda++;
                colaCuerda.signalAll();
            } else {
                // señal para los hilos visitantes que todavia siguen en cola de espera
                colaCuerda.signalAll();
            }
        } catch (InterruptedException e) {
        } finally {
            accesoCuerda.unlock();
        }
    }

    // SALTO

    public void saltar() {
        accesoSalto.lock();
        try {
            while (cantSaltos == 0) {
                colaSalto.await();
            }
            if (t.permisoRealizarActividad()) {
                cantSaltos--;
                System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n" + "La persona "
                        + Thread.currentThread().getName() + " está por saltar" + RESET);
                Thread.sleep(900);
                System.out.println(VERDE + "~~~~ MUNDO AVENTURA ~~~~ \n" + "La persona "
                        + Thread.currentThread().getName() + " acaba de saltar" + RESET);
                cantSaltos++;
                colaSalto.signalAll();
            } else {
                // señal para los hilos visitantes que todavia siguen en cola de espera
                colaSalto.signalAll();
            }
        } catch (Exception e) {
        } finally {
            accesoSalto.unlock();
        }
    }

}