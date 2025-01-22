
public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        NadoDelfines nd = new NadoDelfines();
        ControlPileta cp = new ControlPileta(nd);
        Restaurante[] colRestaurantes = new Restaurante[3];
        Laguna laguna = new Laguna(); 
        AsistenteSnorkel[] asistentesSnorkel = new AsistenteSnorkel[2]; 
        Persona[] p = new Persona[20];

        for (int i = 1; i < p.length; i++) {
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[0], laguna); // siempre arranca viendo al primer
                                                                                     // restaurante
        }
        int cap = 0;
        
        for (int i = 0; i < colRestaurantes.length; i++) {
            colRestaurantes[i] = new Restaurante(i, cap + 5);
        }
        

        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i] = new AsistenteSnorkel(laguna); 
        } 

        
        cp.start();
        for (int i = 1; i < p.length; i++) {
            p[i].start();
        }
        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i].start();
        } 
    }
}