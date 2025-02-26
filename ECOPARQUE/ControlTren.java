public class ControlTren extends Thread {
    private CarreraGomones cg;
    private boolean permiso;

    public ControlTren(CarreraGomones c) {
        this.cg = c;
    }

    public void run() {
        try {
            while (true) {
                permiso = cg.arrancarTren();
                Thread.sleep(700);
                cg.finalizarRecorridoTren(permiso);
                Thread.sleep(700);
                cg.vueltaTren(permiso);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
