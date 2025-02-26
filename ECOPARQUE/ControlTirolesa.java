public class ControlTirolesa extends Thread{
    private MundoAventura ma; 
    
    public ControlTirolesa(MundoAventura m){
        this.ma = m; 
    }

    public void run(){
        while(true){
            try {
                ma.verficarTirolesa();
                Thread.sleep(200);
                ma.llevarTirolesa();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
