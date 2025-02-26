public class ControlTiempo extends Thread{
    private Tiempo tiempo; 
    private NadoDelfines nd; 

    public ControlTiempo(Tiempo t, NadoDelfines nd){
        this.tiempo = t; 
        this.nd = nd; 
    }

    public void run(){
        try {
            while(true){
<<<<<<< HEAD
                Thread.sleep(100);  //simulacion de tiempo para un minuto
                tiempo.aumentarMinuto(); 
                
                //Control del tiempo para las funciones de nado de delfines
=======
                Thread.sleep(200);
                tiempo.aumentarMinuto(); 
    
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
                if(tiempo.getMinuto() == 15){  
                    nd.avisaControlComienzo(); //15 minutos de espera para inicion de funcion
                }else if (tiempo.getMinuto() == 0){ 
                    nd.avisaControlFin();
                }
<<<<<<< HEAD

                
=======
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 07b6ee1f6c7e5ea9ef1bfda6da7bc8570c22c002
