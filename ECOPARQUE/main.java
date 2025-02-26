import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        
        //recursos compartidos 
<<<<<<< HEAD
        Tiempo t = new Tiempo(9, 0);
        NadoDelfines nd = new NadoDelfines(t);
=======
          
        NadoDelfines nd = new NadoDelfines();
        Tiempo t = new Tiempo(9, 0);

>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
        Restaurante[] colRestaurantes = new Restaurante[3];
        colRestaurantes[0] = new Restaurante(1, 2,t);
        colRestaurantes[1] = new Restaurante(2, 5,t);
        colRestaurantes[2] = new Restaurante(3, 7,t);
        Laguna laguna = new Laguna(t); 
        MundoAventura ma = new MundoAventura(t); 
        CarreraGomones cg = new CarreraGomones(t); 
        FaroMirador fa = new FaroMirador();
<<<<<<< HEAD
        Colectivo cole = new Colectivo();
        Parque ecoParque = new Parque(nd, colRestaurantes, laguna, ma, cg, fa);
        // hilos personas
        Persona[] p = new Persona[20];

        for (int i = 0; i < p.length; i++) {
                p[i] = new Persona("persona " + (i),cole, ecoParque,t);
=======
        //hilos personas 
        Persona[] p = new Persona[200];
        
        

        for (int i = 0; i < p.length; i++) {
            Random r2 = new Random();
        int numRestoElegido = r2.nextInt(3);
        if(i%2 == 0){ 
            p[i] = new Persona("persona " + (i),t, nd, i, colRestaurantes[numRestoElegido], laguna, ma, 1, cg, 1,1,fa); // siempre arranca viendo al primer
            // restaurante
        }else {
            p[i] = new Persona("persona " + (i),t, nd, i, colRestaurantes[numRestoElegido], laguna, ma, 2, cg,2,2 ,fa); // siempre arranca viendo al primer
                                                                                 // restaurante
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
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

<<<<<<< HEAD
        // comienzo de hilos controles
        cp.start(); // control pileta
        for (int i = 0; i < asistentesSnorkel.length; i++) {
=======
        //comienzo de hilos controles    
    
      tiempo.start();
      cp.start(); //control pileta

      /* 
        for(int i= 0; i < asistentesSnorkel.length; i++){
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
            asistentesSnorkel[i].start();
        }
        ct.start(); // control tirolesa
        ctren.start(); // control tren
        cFaro.start(); // control faro

<<<<<<< HEAD
        // comienzo de hilos personas
        for (int i = 0; i < p.length; i++) {
=======
     // ct.start(); //control tirolesa
     //ctren.start(); // control tren 
     //  cFaro.start(); //control faro

     //comienzo de hilos personas
        for(int i=0; i< p.length; i++){
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
            p[i].start();
        }
    }

}