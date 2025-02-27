
public class Parque {
    // contiene todas las actividades
    private NadoDelfines pile;
    private Restaurante[] resto;
    private Laguna laguna;
    private MundoAventura ma;
    private CarreraGomones cg;
    private FaroMirador fa;
    // el reloj

    public Parque(NadoDelfines p, Restaurante[] colRestaurantes, Laguna l, MundoAventura m,
            CarreraGomones c, FaroMirador faro) {
        this.pile = p;
        this.laguna = l;
        this.resto = colRestaurantes;
        this.ma = m;
        this.cg = c;
        this.fa = faro;
    }

    public void realizarActividad(int eleccion, Persona visitante, int eleccionResto, int ladoTirolesa,
            int eleccionTransporte, int eleccionGomon) throws InterruptedException {

        switch (eleccion) {

            case 0:// -------------------NADO DELFINES
                pile.solPile();
                pile.abandonarFuncion();
                break;

            case 1: // -------------------SNORKEL
                try {
                    laguna.solicitarEquipo();
                    Thread.sleep(500);
                    laguna.devolverEquipo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                // -------------------RESTAURANTE
                try {

                    Restaurante restaurante = resto[eleccionResto];

                    restaurante.entrarRestaurante(visitante);
                    restaurante.pedirAlmuerzo(visitante);
                    restaurante.salirRestaurante();

                    Thread.sleep(2000);

                    restaurante.entrarRestaurante(visitante);
                    restaurante.pedirMerienda(visitante);
                    restaurante.salirRestaurante();

                } catch (Exception e) {
                }
                break;

            case 3:
                // -------------------MUNDO AVENTURA

                try {

                    switch (ladoTirolesa) {
                        case 1:
                            ma.subirTirolesa();
                            Thread.sleep(600);
                            ma.bajarseTirolesa();
                            break;
                        case 2:
                            ma.subirTirolesa2();
                            Thread.sleep(600);
                            ma.bajarseTirolesa2();
                            break;
                    }
                    ma.usarCuerda();
                    ma.saltar();

                } catch (Exception e) {
                }
                break;

            case 4:
                // -------------------CARRERA DE GOMONES

                try {
                    switch (eleccionTransporte) {
                        case 1:
                            cg.subirTren();
                            break;

                        case 2:
                            cg.subirBici(); 
                            Thread.sleep(1800);
                            cg.dejarBici();
                            break;
                    }
                } catch (Exception e) {
                }

                try {
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

                } catch (Exception e) {
                }
                break;

            case 5:
                // -------------------FARO MIRADOR
                try {
                    fa.ingresar();
                    fa.avisaControl(); // despierto al control para que me diga a cual voy
                    boolean opcion = fa.esperarTurno();

                    fa.subirTobogan(opcion);

                    // Thread.sleep(1000);
                    // fa.bajarTobogan1(opcion);

                } catch (Exception e) {
                }
                break;
        }

    }
}