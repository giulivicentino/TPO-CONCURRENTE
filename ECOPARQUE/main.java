import java.util.Random;

public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        
        //recursos compartidos 
          
        Tiempo t = new Tiempo(9, 0);
       
        NadoDelfines nd = new NadoDelfines(t);
        Restaurante[] colRestaurantes = new Restaurante[3];
        colRestaurantes[0] = new Restaurante(1, 20,t);
        colRestaurantes[1] = new Restaurante(2, 30,t);
        colRestaurantes[2] = new Restaurante(3, 40,t);

        Laguna laguna = new Laguna(t); 
        MundoAventura ma = new MundoAventura(t); 
        CarreraGomones cg = new CarreraGomones(t); 
        FaroMirador fa = new FaroMirador();
        
        
        //hilos personas 
        Persona[] p = new Persona[500];
        
        
        for (int i = 0; i < p.length; i++) {
            Random r2 = new Random();
        int numRestoElegido = r2.nextInt(3);
        if(i%2 == 0){ 
            p[i] = new Persona("persona " + (i),t, nd, colRestaurantes[numRestoElegido], laguna, ma, 1, cg, 1,1,fa); // siempre arranca viendo al primer
            // restaurante
        }else {
            p[i] = new Persona("persona " + (i),t, nd, colRestaurantes[numRestoElegido], laguna, ma, 2, cg,2,2 ,fa); // siempre arranca viendo al primer
                                                                                 // restaurante
        }
           
        }




        //hilos controles 
        ControlTiempo tiempo = new ControlTiempo(t, nd); 
        ControlPileta cp = new ControlPileta(nd);
        AsistenteSnorkel[] asistentesSnorkel = new AsistenteSnorkel[2]; 
        ControlTirolesa ct = new ControlTirolesa(ma); 
        ControlTren ctren = new ControlTren(cg); 
        ControlFaro cFaro = new ControlFaro(fa);
        
        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i] = new AsistenteSnorkel(laguna); 
        } 

        //comienzo de hilos controles    
    
      tiempo.start(); //control tiempo
      cp.start(); //control pileta

       
      for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i].start(); //asistentes de snorkel
      } 
     

      ct.start(); //control tirolesa
      ctren.start(); // control tren 
      cFaro.start(); //control faro

     //comienzo de hilos personas
        for(int i=0; i< p.length; i++){
            p[i].start();
        }
    }
}