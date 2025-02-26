import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        
        //recursos compartidos 
        Tiempo t = new Tiempo(9, 0);
        NadoDelfines nd = new NadoDelfines(t);
        Restaurante[] colRestaurantes = new Restaurante[3];
        colRestaurantes[0] = new Restaurante(1, 2,t);
        colRestaurantes[1] = new Restaurante(2, 5,t);
        colRestaurantes[2] = new Restaurante(3, 7,t);
        Laguna laguna = new Laguna(t); 
        MundoAventura ma = new MundoAventura(t); 
        CarreraGomones cg = new CarreraGomones(t); 
        FaroMirador fa = new FaroMirador();
        Colectivo cole = new Colectivo();
        Parque ecoParque = new Parque(nd, colRestaurantes, laguna, ma, cg, fa);
        // hilos personas
        Persona[] p = new Persona[20];

        for (int i = 0; i < p.length; i++) {
                p[i] = new Persona("persona " + (i),cole, ecoParque,t);
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

        // comienzo de hilos controles
        cp.start(); // control pileta
        for (int i = 0; i < asistentesSnorkel.length; i++) {
            asistentesSnorkel[i].start();
        }
        ct.start(); // control tirolesa
        ctren.start(); // control tren
        cFaro.start(); // control faro

        // comienzo de hilos personas
        for (int i = 0; i < p.length; i++) {
            p[i].start();
        }
    }

}