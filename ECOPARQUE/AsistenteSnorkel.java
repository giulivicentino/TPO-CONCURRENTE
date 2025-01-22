public class AsistenteSnorkel  extends Thread{
    private Laguna laguna; 
    
    public AsistenteSnorkel(Laguna l) {
        laguna = l; 
    }

    public void run(){
        while(true){
            try {
                laguna.otorgarEquipo();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }            
        }
    }

}
