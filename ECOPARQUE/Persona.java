import java.util.Random;

public class Persona extends Thread {
    
     private Parque parque; // contiene todas las actividades
     private Colectivo cole;


    public Persona(String n, NadoDelfines p, int h, Restaurante res, Laguna l, MundoAventura m, int ladoTirolesa,
            CarreraGomones c, int eleccionTransporte, int eleccionGomon, FaroMirador faro) {
        this.pile = p;
        this.horarioPile = h;
        this.setName(n);
        this.cole=cole;
        this.parque = unParque;
            }

    public void run() {

        // -------------------NADO DELFINES
        // pile.solPile(); // desp habria q agregarle q sea random lo q deciden hacer

        // -------------------SNORKEL

        /*
         * try {
         * 
         * laguna.solicitarEquipo();
         * Thread.sleep(2000);
         * laguna.devolverEquipo();
         * 
         * } catch (InterruptedException e) {
         * e.printStackTrace();
         * }
         * 
         * }
         * 
         * 
         * 
         * //-------------------MUNDO AVENTURA
         * 
         * try {
         * 
         * 
         * switch (ladoTirolesa) {
         * case 1:
         * System.out.println("p este");
         * ma.subirTirolesa();
         * Thread.sleep(200);
         * ma.bajarseTirolesa();
         * break;
         * 
         * case 2:
         * System.out.println("p oeste");
         * ma.subirTirolesa2();
         * Thread.sleep(200);
         * ma.bajarseTirolesa2();
         * 
         * 
         * break;
         * }
         * 
         * ma.usarCuerda();
         * Thread.sleep(400);
         * ma.dejarCuerda();
         * 
         * ma.saltar();
         * Thread.sleep(200);
         * ma.dejarSalto();
         * 
         * 
         * } catch (Exception e) {
         * 
         * }
         * 
         * //-------------------RESTAURANTE
         * try {
         * 
         * resto.entrarRestaurante(this);
         * resto.pedirAlmuerzo(this);
         * resto.salirRestaurante();
         * 
         * Thread.sleep(2000);// lo dejo asi para probar, pero tendria que ser que lo
         * puedan pedir entre
         * // actividades tmb
         * 
         * resto.entrarRestaurante(this);
         * resto.pedirMerienda(this);
         * resto.salirRestaurante();
         * 
         * } catch (Exception e) {
         * 
         * }
         */

        // -------------------CARRERA DE GOMONES

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

         

    }
}