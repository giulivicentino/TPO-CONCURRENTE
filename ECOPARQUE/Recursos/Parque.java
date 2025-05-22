package Recursos;

import Hilos.Persona;

public class Parque {
    // contiene todas las actividades
    private NadoDelfines pile;
    private Restaurante[] resto;
    private Laguna laguna;
    private MundoAventura ma;
    private CarreraGomones cg;
    private FaroMirador fa;
    private Shop tienda;

    public Parque(NadoDelfines p, Restaurante[] colRestaurantes, Laguna l, MundoAventura m,
            CarreraGomones c, FaroMirador faro, Shop tiendita) {
        this.pile = p;
        this.laguna = l;
        this.resto = colRestaurantes;
        this.ma = m;
        this.cg = c;
        this.fa = faro;
        this.tienda = tiendita;
    }

    public void realizarActividad(int eleccion, Persona visitante, int eleccionResto, int ladoTirolesa,
            int eleccionTransporte, int eleccionGomon, int eleccionComida) throws InterruptedException {

        switch (eleccion) {

            case 0:// -------------------NADO DELFINES
                pile.solPile();
                pile.abandonarFuncion();
                break;

            case 1: // -------------------SNORKEL
                laguna.solicitarEquipo();
                break;

            case 2: // -------------------RESTAURANTE
                try {
                    switch (eleccionResto) {
                        case 1:
                            resto[0].entraRestaurante(visitante, eleccionComida);

                            switch (eleccionComida) {
                                case 1:
                                    resto[0].pedirAlmuerzo(visitante);
                                    break;

                                case 2:
                                    resto[0].pedirMerienda(visitante);
                                    break;
                            }
                            Thread.sleep(500);
                            resto[0].salirRestaurante();
                            break;

                        case 2:
                            resto[1].entraRestaurante(visitante, eleccionComida);
                            Thread.sleep(500);
                            switch (eleccionComida) {
                                case 1:
                                    resto[1].pedirAlmuerzo(visitante);
                                    break;

                                case 2:
                                    resto[1].pedirMerienda(visitante);
                                    break;
                            }
                            resto[1].salirRestaurante();
                            break;
                        case 3:
                            resto[2].entraRestaurante(visitante, eleccionComida);
                            Thread.sleep(500);
                            switch (eleccionComida) {
                                case 1:
                                    resto[2].pedirAlmuerzo(visitante);
                                    break;

                                case 2:
                                    resto[2].pedirMerienda(visitante);
                                    break;
                            }
                            resto[2].salirRestaurante();
                            break;
                    }
                } catch (Exception e) {
                }
                ;
                break;

            case 3: // -------------------MUNDO AVENTURA

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
                    Thread.sleep(600);
                    ma.saltar();

                } catch (Exception e) {
                }
                break;

            case 4: // -------------------CARRERA DE GOMONES

                try {
                    switch (eleccionTransporte) {
                        case 1:
                            cg.subirTren();
                            break;

                        case 2:
                            cg.subirBici();
                            break;
                    }
                } catch (Exception e) {
                }

                try {
                    switch (eleccionGomon) {
                        case 1: // caso gomon doble
                            cg.carrera(cg.elegirGomon(true), true);
                            break;

                        case 2: // caso gomon simple
                            cg.carrera(cg.elegirGomon(false), false);
                            break;
                    }

                } catch (Exception e) {
                }
                break;

            case 5: // -------------------FARO MIRADOR
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

            case 6: // ---------------------SHOP
                try {
                    tienda.comprar();
                    Thread.sleep(1000);
                    tienda.esperaPagar();
                    tienda.irseTienda();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}