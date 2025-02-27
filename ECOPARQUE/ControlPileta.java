
public class ControlPileta extends Thread {
    private NadoDelfines nado;
  
    public ControlPileta(NadoDelfines nd) {
        this.nado = nd;
    }

    public void run() {
        while (true) {

            try {
                Thread.sleep(200); // simulo tiempo entre las funciones
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            nado.comenzarFuncion();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nado.terminarFuncion();
        }
    }
}
