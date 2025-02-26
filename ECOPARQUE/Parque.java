import java.util.Random;

public class Parque {
    // que tenga todas las actividades
    private NadoDelfines pile;
    private Restaurante[] resto;
    private Laguna laguna;
    private MundoAventura ma;
    private int ladoTirolesa;
    private CarreraGomones cg;
    private FaroMirador fa;
    private int horarioPile;
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



    public void realizarActividad(int eleccion, Persona visitante)throws InterruptedException {
        //if (horarioPermitido) { // esto es con reloj
            switch (eleccion) {
                case 0:// -------------------NADO DELFINES
                    pile.solPile();  break;
                case 1: // -------------------SNORKEL
                    try {
                        laguna.solicitarEquipo();
                        Thread.sleep(2000);
                        laguna.devolverEquipo();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } break;
                case 2:
                    //-------------------RESTAURANTE
          try {
            Random r1 = new Random();
            int eleccionResto= r1.nextInt(0,3);
            Restaurante restaurante = resto[eleccionResto];

          
            restaurante.entrarRestaurante(visitante);
            restaurante.pedirAlmuerzo(visitante);
            restaurante.salirRestaurante();
                                                                            // va partido, se llama dos veces a lo mismo
            Thread.sleep(2000);
            
            restaurante.entrarRestaurante(visitante);
            restaurante.pedirMerienda(visitante);
            restaurante.salirRestaurante();
            
            } catch (Exception e) {}break;
                
            case 3:   
          //-------------------MUNDO AVENTURA
          
     try {
         Random r3 = new Random();
         int ladoTirolesa = r3.nextInt(1,2);
        switch (ladoTirolesa) {
            case 1:
                System.out.println("p este");
                ma.subirTirolesa();
                Thread.sleep(200);
                ma.bajarseTirolesa();break;
            case 2:
                System.out.println("p oeste");
                ma.subirTirolesa2();
                Thread.sleep(200);
                ma.bajarseTirolesa2(); break;
        }
        ma.usarCuerda();
        Thread.sleep(400);
        ma.dejarCuerda();
        ma.saltar();
        Thread.sleep(200);
        ma.dejarSalto();
    } catch (Exception e) {}break;
        case 4:
                   

        // -------------------CARRERA DE GOMONES
        Random r4 = new Random();
         int eleccionTransporte= r4.nextInt(1,2);
        try {
            switch (eleccionTransporte) {
                case 1:
                    cg.subirTren();
                    break;

                case 2:
                    cg.subirBici();
                    Thread.sleep(6000);
                    cg.dejarBici();
                    break;
            }
        } catch (Exception e) {
        }

        Random r5 = new Random();
        int eleccionGomon= r4.nextInt(1,2);
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
                    
                    break;
                default:
                    break;
            }
            

    }
}