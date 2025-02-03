import java.util.Random;

public class Persona extends Thread {
    // todas las actividades que podría realizar
    private NadoDelfines pile;
    private Restaurante resto;
    private Laguna laguna;
    private MundoAventura ma; 
    private int ladoTirolesa; 

    private int horarioPile;

    public Persona(String n, NadoDelfines p, int h, Restaurante res, Laguna l, MundoAventura m, int ladoTirolesa) {
        this.pile = p;
        this.horarioPile = h;
        this.setName(n);
        this.resto = res;
        this.laguna = l;
        this.ma = m; 
        this.ladoTirolesa = ladoTirolesa; 
    }

    public void run() {
        
//-------------------NADO DELFINES
        // pile.solPile(); // desp habria q agregarle q sea random lo q deciden hacer


//-------------------SNORKEL
/* 
        try {

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
*/
//-------------------RESTAURANTE



    }
}