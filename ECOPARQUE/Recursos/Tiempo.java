package Recursos;
public class Tiempo {
   public static final String MAGENTA = "\u001B[35m"; // colores para la salida por pantalla (mas legible)
   public static final String RESET = "\u001B[0m"; // colores para la salida por pantalla (mas legible)

   private int hora;
   private int minuto;

   public Tiempo(int hora, int minuto) {
      this.hora = hora;
      this.minuto = minuto;
   }

   public boolean verificarHora() {
      // metodo para verificar que el visitante pueda seguir realizando actividades
      return hora >= 9 && hora < 18;
   }

   public boolean verificarIngreso() {
      // metodo para verificar que una persona pueda ingresar al parque
      return hora >= 9 && hora < 17;
   }

   public boolean permisoRealizarActividad() {
      return hora < 17 || (hora == 17 && minuto < 50);
   }

   public void aumentarHora() {
      this.hora++;
      System.out.println(MAGENTA + "------------------------------- \n"
            + "       NUEVA HORA:" + hora + "        \n"
            + "------------------------------- " + RESET);
      if (this.hora == 17) {
         System.out.println(MAGENTA + "-------------------------------  \n"
               + " EL PARQUE CIERRA SU INGRESO \n"
               + "-------------------------------" + RESET);
      }
      if (this.hora == 18) {
         System.out.println(MAGENTA + "------------------------------- \n"
               + "      EL PARQUE CERRÓ \n"
               + "-------------------------------" + RESET);
      }
   }

   public void aumentarMinuto() {
      this.minuto++;
      if (this.minuto == 60) {
         aumentarHora();
         this.minuto = 0;
      }
   }

   public void resetearMinuto() {
      this.minuto = 0;
   }

   public int getMinuto() {
      return minuto;
   }

   public int getHora() {
      return hora;
   }

}
