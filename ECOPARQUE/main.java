
public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        NadoDelfines nd = new NadoDelfines();
        ControlPileta cp = new ControlPileta(nd);
        Restaurante[] colRestaurantes = new Restaurante[3];
        Persona[] p = new Persona[100];

        for (int i = 1; i < p.length; i++) {
            p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[0]); // siempre arranca viendo al primer
                                                                             // restaurante
        }
        int cap = 0;
        for (int i = 1; i < 4; i++) {
            colRestaurantes[i] = new Restaurante(i, cap + 5);
        }

        cp.start();
        for (int i = 1; i < p.length; i++) {
            p[i].start();
        }
    }
}