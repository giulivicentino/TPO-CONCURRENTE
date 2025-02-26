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



    public void realizarActividad(int eleccion, Persona visitante, int eleccionResto,int ladoTirolesa, int eleccionTransporte, int eleccionGomon)throws InterruptedException {
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
       
        try {
            switch (eleccionTransporte) {
                case 1:
                    cg.subirTren();
                    break;

                case 2:
                    Boolean puedeBajar=cg.subirBici(); //momentaneo!!!!
                    Thread.sleep(6000);
                    cg.dejarBici(puedeBajar);
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
            // TODO: handle exception
        }
        break;

                
            

    }
}