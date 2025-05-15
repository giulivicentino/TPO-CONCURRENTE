package Recursos;

public class Colectivo {
    private int capacidadCole = 25;
    private boolean puedeSubir = true;
    private int cantidadEnCole;
    private Tiempo t;
    private int id;

    public static final String VERDE = "\u001B[38;5;46m"; // Verde en la paleta de 256 colores
    public static final String ROJO = "\u001B[38;5;196m"; // Rojo en la paleta de 256 colores
    public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)

    public Colectivo(Tiempo tiempo, int id) {
        this.t = tiempo;
        this.id = id;
    }

    // metodos del visitante
    public synchronized boolean subirCole() throws InterruptedException {
        boolean subio = false;
        if (t.verificarIngreso()) {
            try {
                while (!puedeSubir) { // no sube si esta lleno o si esta en viaje
                    this.wait();
                }

                cantidadEnCole++;
                if (cantidadEnCole == capacidadCole) {
                    puedeSubir = false;
                }
                subio = true;
                System.out.println(VERDE + "```` INGRESO POR COLECTIVO " + id + " ```` \n" + "La persona "
                        + Thread.currentThread().getName() + "se SUBE al colectivo , cantidad en cole: "
                        + cantidadEnCole + RESET);
            } catch (InterruptedException ex) {
            }
        } else {
            subio = false; // no sube por que ya es tarde
        }
        return subio;
    }

    public synchronized void bajarCole(boolean subio) throws InterruptedException {
        if (subio) {
            cantidadEnCole--;

            System.out.println(ROJO + "```` INGRESO POR COLECTIVO " + id + " ```` \n" + "La persona "
                    + Thread.currentThread().getName() + "se BAJA del colectivo,  cantidad en cole: " + cantidadEnCole
                    + RESET);
            this.notifyAll(); // "actualiza" a los que esperaban
        }
    }

    // metodos del colectivero
    public synchronized void arrancarCole() throws InterruptedException {
        puedeSubir = false;
        System.out.println(" ..... COLECTIVO " + id + "  EN VIAJE .....");
    }

    public synchronized void vueltaCole() throws InterruptedException {
        while (cantidadEnCole != 0) { // espera a que se bajen todos para "reiniciar"
            this.wait();
        }
        puedeSubir = true;
        System.out.println(" ..... COLECTIVO " + id + " VUELVE EXITOSAMENTE .....");
        this.notifyAll();
    }

}