package ECOPARQUE; 

public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        NadoDelfines nd = new NadoDelfines();
        ControlPileta cp = new ControlPileta(nd);
        Persona[] p = new Persona[100]; 
        
        for(int i= 1; i < p.length; i++){
            p[i] = new Persona("persona "+(i), nd, i);  
        }

        cp.start();
        for(int i= 1; i < p.length; i++){
            p[i].start();  
        }
    }
}