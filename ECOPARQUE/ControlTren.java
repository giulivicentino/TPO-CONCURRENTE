public class ControlTren extends Thread{
    private CarreraGomones cg; 

    public ControlTren(CarreraGomones c){
        this.cg = c; 
    }

    public void run(){
        try {
            while(true){
            cg.arrancarTren();
            Thread.sleep(2000);
            cg.finalizarRecorridoTren();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
