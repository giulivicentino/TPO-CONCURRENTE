public class Tiempo {
 private int hora; 
 private int minuto; 

 public Tiempo(int hora, int minuto){
    this.hora = hora; 
    this.minuto = minuto; 
 }

 public boolean verificarHora(){
    //metodo para verificar que el visitante pueda seguir realizando actividades
    return hora >= 9 && hora < 11; 
 }

 public boolean verificarIngreso(){
    //metodo para verificar que una persona pueda ingresar al parque
    return hora >= 9 && hora < 10; 
 }


 public void aumentarHora(){
    this.hora++; 
    if(this.hora == 10){
      System.out.println("--- EL PARQUE CIERRA SU INGRESO ---");
    }
    if(this.hora == 11){
      System.out.println("--- EL PARQUE CERRÓ ---");
    }
 }

 public void aumentarMinuto(){
    this.minuto++;
    System.out.println(this.minuto);
    if(this.minuto == 60){
        aumentarHora();
        this.minuto = 0; 
    }
 }

 public void resetearMinuto(){
   this.minuto = 0; 
 }

 public int getMinuto(){
    return minuto; 
 }

 public int getHora(){
    return hora; 
 }

}
