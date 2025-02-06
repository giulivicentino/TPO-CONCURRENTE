public class ControlFaro extends Thread{
    private FaroMirador fa; 
    
    public ControlFaro(FaroMirador farito){
        this.fa = farito; 
    }

    public void run(){
        while(true){
            try {
                fa.seleccionarTobogan();
                fa.avisarVisitante();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
