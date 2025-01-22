
public class Persona extends Thread {
    // todas las actividades que podría realizar
    private NadoDelfines pile;
    private Restaurante resto;
    private Laguna laguna;

    private int horarioPile;

    public Persona(String n, NadoDelfines p, int h, Restaurante res, Laguna l) {
        this.pile = p;
        this.horarioPile = h;
        this.setName(n);
        this.resto = res;
        this.laguna = l;
    }

    public void run() {
        // pile.solPile(); // desp habria q agregarle q sea random lo q deciden hacer

        // parte del snorkel
        try {

            laguna.solicitarEquipo();
            Thread.sleep(2000);
            laguna.devolverEquipo();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
