import java.util.Random;

public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        
        //recursos compartidos 

        NadoDelfines nd = new NadoDelfines();
        
        Restaurante[] colRestaurantes = new Restaurante[3];
        colRestaurantes[0] = new Restaurante(1, 2);
        colRestaurantes[1] = new Restaurante(2, 5);
        colRestaurantes[2] = new Restaurante(3, 7);

        Laguna laguna = new Laguna(); 
        MundoAventura ma = new MundoAventura(); 
        CarreraGomones cg = new CarreraGomones(); 
        FaroMirador fa = new FaroMirador();
        //hilos personas 
        Persona[] p = new Persona[22];
        
        

        for (int i = 0; i < p.length; i++) {
            Random r2 = new Random();
            Random r3 = new Random();
        int numRestoElegido = r2.nextInt(3);
        boolean gomon = r3.nextBoolean();
        if(i%2 == 0){ 
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[numRestoElegido], laguna, ma, 1, cg, 1,gomon,fa); // siempre arranca viendo al primer
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[numRestoElegido], laguna, ma, 1, cg, 1,1,fa); // siempre arranca viendo al primer
            // restaurante
        }else {
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[numRestoElegido], laguna, ma, 2, cg,2,gomon,fa); // siempre arranca viendo al primer restaurante
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[numRestoElegido], laguna, ma, 2, cg,2,2 ,fa); // siempre arranca viendo al primer
                                                                                 // restaurante
        }
           
        }




        //hilos controles 
        ControlPileta cp = new ControlPileta(nd);
        AsistenteSnorkel[] asistentesSnorkel = new AsistenteSnorkel[2]; 
        ControlTirolesa ct = new ControlTirolesa(ma); 
        ControlTren ctren = new ControlTren(cg); 
        ControlFaro cFaro = new ControlFaro(fa);
        
        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i] = new AsistenteSnorkel(laguna); 
        } 

        //comienzo de hilos controles        
      //  cp.start();

      /* 
        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i].start();
        } 
     */

     //ctren.start();
        cFaro.start();

     // ct.start();
     
     ctren.start();

     //comienzo de hilos personas
        for(int i=0; i< p.length; i++){
            p[i].start();
        }
    }
}