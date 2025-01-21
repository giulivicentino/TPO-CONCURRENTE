
public class Persona extends Thread {
    // todas las actividades que podría realizar
    private NadoDelfines pile;
    private Restaurante resto;

    private int horarioPile;

    public Persona(String n, NadoDelfines p, int h, Restaurante res) {
        this.pile = p;
        this.horarioPile = h;
        this.setName(n);
        this.resto = res;
    }

    public void run() {
        // pile.solPile(); // desp habria q agregarle q sea random lo q deciden hacer
    }

}
