import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Persona extends Thread {
    // todas las actividades que podría realizar
    private Tiempo tiempo;
    private NadoDelfines pile;
    private Restaurante resto;
    private Laguna laguna;
    private MundoAventura ma;
    private int ladoTirolesa;
    private CarreraGomones cg;
    private int eleccionTransporte;
    private int eleccionGomon;
    private boolean permisoAcceso;
    private FaroMirador fa;
    private int eleccionActividad;
    Random r = new Random();

    public Persona(String n, Tiempo t, NadoDelfines p, Restaurante res, Laguna l, MundoAventura m, int ladoTirolesa,
            CarreraGomones c, int eleccionTransporte, int eleccionGomon, FaroMirador faro) {
        this.tiempo = t;
        this.pile = p;
        this.setName(n);
        this.resto = res;
        this.laguna = l;
        this.ma = m;
        this.ladoTirolesa = ladoTirolesa;
        this.cg = c;
        this.eleccionTransporte = eleccionTransporte;
        this.eleccionGomon = eleccionGomon;
        this.fa = faro;
    }

    public void run() {

        if (tiempo.verificarIngreso()) { // Verifica si puede ingresar al parque
            eleccionActividad = 5;

            while (tiempo.verificarHora()) { // Verifica si puede realizar alguna actividad

                switch (eleccionActividad) {
                    case 1:
                        // -------------NADO DELFINES
                        pile.solPile();
                        pile.abandonarFuncion();
                        break;

                    case 2:
                        // -------------------SNORKEL
                        try {
                            laguna.solicitarEquipo();
                            Thread.sleep(2000);
                            laguna.devolverEquipo();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 3:
                        // -------------------MUNDO AVENTURA
                        try {

                            switch (ladoTirolesa) {
                                case 1:
                                    ma.subirTirolesa();
                                    Thread.sleep(200);
                                    ma.bajarseTirolesa();
                                    break;

                                case 2:
                                    ma.subirTirolesa2();
                                    Thread.sleep(200);
                                    ma.bajarseTirolesa2();
                                    break;
                            }

                            ma.usarCuerda();
                            Thread.sleep(200);
                            ma.dejarCuerda();

                            ma.saltar();
                            Thread.sleep(200);
                            ma.dejarSalto();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        // -------------------RESTAURANTE
                        try {

                            resto.entrarRestaurante(this);
                            resto.pedirAlmuerzo(this);
                            resto.salirRestaurante();

                            Thread.sleep(2000);

                            resto.entrarRestaurante(this);
                            resto.pedirMerienda(this);
                            resto.salirRestaurante();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 5:
                        // -------------------CARRERA DE GOMONES
                        try {
                            switch (eleccionTransporte) {
                                case 1:
                                    permisoAcceso = cg.subirTren();
                                    cg.bajarTren(permisoAcceso);
                                    break;

                                case 2:
                                    permisoAcceso = cg.subirBici();
                                    Thread.sleep(1000);
                                    cg.dejarBici(permisoAcceso);
                                    break;
                            }
                        } catch (Exception e) {
                        }
                        if (permisoAcceso) {
                            try {
                                Thread.sleep(100);
                                switch (eleccionGomon) {
                                    case 1: // caso gomon doble
                                        if (cg.elegirGomon(true)) {
                                            cg.carrera();
                                            cg.devolverGomon(true);

                                        } else {
                                            Thread.sleep(4000);
                                        }
                                        break;

                                    case 2: // caso gomon simple
                                        cg.elegirGomon(false);
                                        cg.carrera();
                                        cg.devolverGomon(false);
                                        break;
                                }

                            } catch (InterruptedException | BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }

                        break;

                    case 6:
                        // -------------------FARO MIRADOR
                        try {

                            fa.ingresar();
                            fa.avisaControl(); // despierto al control para que me diga a cual voy
                            boolean opcion = fa.esperarTurno();

                            fa.subirTobogan(opcion);
                            // Thread.sleep(1000);
                            // fa.bajarTobogan1(opcion);

                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        break;

                    case 7:
                        // -------------------ABANDONAR PARQUE
                        break;
                }

                // eleccionActividad = r.nextInt(7) + 1;

            }
        } else {
            System.out.println("YA NO SE PUEDE INGRESAR");
        }
    }

}