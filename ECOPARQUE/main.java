
public class main {
    /**
     * @param args
     */

    public static void main(String[] args) {
        
        //recursos compartidos 

        NadoDelfines nd = new NadoDelfines();
        Restaurante[] colRestaurantes = new Restaurante[3];
        Laguna laguna = new Laguna(); 
        MundoAventura ma = new MundoAventura(); 

        int cap = 0;
        for (int i = 1; i < 4; i++) {
            colRestaurantes[i] = new Restaurante(i, cap + 5); //restaurante 1, capacidad 5
        }


        //hilos personas 
        Persona[] p = new Persona[20];

       
        for (int i = 0; i < p.length; i++) {
            
            if(i%2 == 0){ 
                p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[0], laguna, ma, 1); // siempre arranca viendo al primer
                // restaurante
            }else {
                p[i] = new Persona("persona " + (i), nd, i, colRestaurantes[0], laguna, ma, 2); // siempre arranca viendo al primer
                                                                                     // restaurante
            }
           
        }




        //hilos controles 
        ControlPileta cp = new ControlPileta(nd);
        AsistenteSnorkel[] asistentesSnorkel = new AsistenteSnorkel[2]; 
        ControlTirolesa ct = new ControlTirolesa(ma); 
       
        
        
        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i] = new AsistenteSnorkel(laguna); 
        } 

        //comienzo de hilos controles        
      //  cp.start();

      /* 
        for(int i= 0; i < asistentesSnorkel.length; i++){
            asistentesSnorkel[i].start();
        } 
     */

       


        //comienzo de hilos personas
        for(int i=0; i< p.length; i++){
            p[i].start();
        }
        ct.start();
    }
}