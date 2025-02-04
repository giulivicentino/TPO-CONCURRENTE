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
        
        //hilos personas 
        Persona[] p = new Persona[20];
        
        

        for (int i = 0; i < p.length; i++) {
            Random r2 = new Random();
        int numRestoElegido = r2.nextInt(3);
        if(i%2 == 0){ 
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[numRestoElegido], laguna, ma, 1, cg, 1); // siempre arranca viendo al primer
            // restaurante
        }else {
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[numRestoElegido], laguna, ma, 2, cg, 1); // siempre arranca viendo al primer
                                                                                 // restaurante
        }
           
        }




        //hilos controles 
        ControlPileta cp = new ControlPileta(nd);
        AsistenteSnorkel[] asistentesSnorkel = new AsistenteSnorkel[2]; 
        ControlTirolesa ct = new ControlTirolesa(ma); 
        ControlTren ctren = new ControlTren(cg); 
        
        
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

     ctren.start();


        //comienzo de hilos personas
        for(int i=0; i< p.length; i++){
            p[i].start();
        }
        ct.start();
    }
}