import Hilos.AsistenteSnorkel;
import Hilos.ControlFaro;
import Hilos.ControlPileta;
import Hilos.ControlTiempo;
import Hilos.ControlTirolesa;
import Hilos.ControlTren;
import Hilos.Persona;
import Recursos.CarreraGomones;
import Recursos.Colectivo;
import Recursos.FaroMirador;
import Recursos.Laguna;
import Recursos.MundoAventura;
import Recursos.NadoDelfines;
import Recursos.Parque;
import Recursos.Restaurante;
import Recursos.Tiempo;

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
        FaroMirador fa = new FaroMirador(t);
        Colectivo cole = new Colectivo();
        Parque ecoParque = new Parque(nd, colRestaurantes, laguna, ma, cg, fa);
        
        
        // hilos personas
        Persona[] p = new Persona[400];

        for (int i = 0; i < p.length; i++) {
                p[i] = new Persona("persona " + (i),cole, ecoParque,t);
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

        // comienzo de hilos controles
        cp.start(); // control pileta
        for (int i = 0; i < asistentesSnorkel.length; i++) {
            asistentesSnorkel[i].start();
        }
        ct.start(); // control tirolesa
        ctren.start(); // control tren
        cFaro.start(); // control faro
        tiempo.start();
        // comienzo de hilos personas
        for (int i = 0; i < p.length; i++) {
            p[i].start();
        }
    }

}