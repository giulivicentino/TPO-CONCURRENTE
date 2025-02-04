import java.util.Random;

public class Persona extends Thread {
    // todas las actividades que podría realizar
    private NadoDelfines pile;
    private Restaurante resto;
    private Laguna laguna;
    private MundoAventura ma; 
    private int ladoTirolesa; 
    private CarreraGomones cg; 
    private int eleccionTransporte; 

    private int horarioPile;

    public Persona(String n, NadoDelfines p, int h, Restaurante res, Laguna l, MundoAventura m, int ladoTirolesa, CarreraGomones c, int eleccionTransporte) {
        this.pile = p;
        this.horarioPile = h;
        this.setName(n);
        this.resto = res;
        this.laguna = l;
        this.ma = m; 
        this.ladoTirolesa = ladoTirolesa; 
        this.cg = c; 
        this.eleccionTransporte = eleccionTransporte; 
    }

    public void run() {
        
        //-------------------NADO DELFINES
        // pile.solPile(); // desp habria q agregarle q sea random lo q deciden hacer


        //-------------------SNORKEL
         
       /* try {

            laguna.solicitarEquipo();
            Thread.sleep(2000);
            laguna.devolverEquipo();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

     }
        */


         //-------------------MUNDO AVENTURA
        /* 
        try {
          
            
            switch (ladoTirolesa) {
                case 1:
                System.out.println("p este");
                ma.subirTirolesa();
                Thread.sleep(200);
                ma.bajarseTirolesa();
                    break;
            
                case 2:
                System.out.println("p oeste");
                ma.subirTirolesa2();
                Thread.sleep(200);
                ma.bajarseTirolesa2();
                

                break;
            }
           
            ma.usarCuerda();
            Thread.sleep(400);
            ma.dejarCuerda();
            
            ma.saltar();
            Thread.sleep(200);
            ma.dejarSalto();


        } catch (Exception e) {
           
        }
        
        //-------------------RESTAURANTE
        try {

            resto.entrarRestaurante(this);
            resto.pedirAlmuerzo(this);
            resto.salirRestaurante();

            Thread.sleep(2000);// lo dejo asi para probar, pero tendria que ser que lo puedan pedir entre
                       // actividades tmb

            resto.entrarRestaurante(this);
            resto.pedirMerienda(this);
             resto.salirRestaurante();

        } catch (Exception e) {

        }*/
        //-------------------CARRERA DE GOMONES
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
    }
}