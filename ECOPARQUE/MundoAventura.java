import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MundoAventura { // recurso compartido por tirolesas, cuerdas y saltos
    private int cantSaltos;
    private int cuerda;
    private int cantEsperandoLado1;
    private int cantEsperandoLado2; 
    private int cantTirolesas;
    private Lock accesoTirolesa;
    private Lock accesoCuerda;
    private Lock accesoSalto;
    private Condition colaTirolesa1; // representa un lado de la tirolesa
    private Condition colaTirolesa2; // representa el otro lado de la tirolesa
    private Condition colaSalto;
    private Condition colaCuerda;
    Semaphore semControl = new Semaphore(0); 

    public MundoAventura() {
        this.cantSaltos = 2;
        this.cantTirolesas = 2;
        this.cuerda = 1; 
        this.accesoTirolesa = new ReentrantLock(true);
        this.accesoCuerda = new ReentrantLock(true);
        this.accesoSalto = new ReentrantLock(true);
        colaTirolesa1 = accesoTirolesa.newCondition();
        colaTirolesa2 = accesoTirolesa.newCondition();
        colaCuerda = accesoCuerda.newCondition();
        colaSalto = accesoSalto.newCondition();
    }

    // Tirolesa

    public void subirTirolesa() {
        try {
            accesoTirolesa.lock();
            cantEsperandoLado1++;
            while (cantTirolesas == 0) {
                colaTirolesa1.await();
            }
            cantTirolesas--;
            cantEsperandoLado1--;
            System.out.println("La persona " + Thread.currentThread().getName()
                    + " se encuentra utilizando la tirolesa de ESTE a OESTE, tirolesas disponibles: " + cantTirolesas);
        } catch (InterruptedException e) {

        } finally {
            accesoTirolesa.unlock();
        }
    }

    
    public void subirTirolesa2() {
        try {
            accesoTirolesa.lock();
            cantEsperandoLado2++;
            while (cantTirolesas == 0) {
                colaTirolesa1.await();
            }
            cantTirolesas--;
            cantEsperandoLado2--;
            System.out.println("La persona " + Thread.currentThread().getName()
                    + " se encuentra utilizando la tirolesa de OESTE a ESTE, tirolesas disponibles: " + cantTirolesas);
        } catch (InterruptedException e) {

        } finally {
            accesoTirolesa.unlock();
        }
    }

    public void bajarseTirolesa() {
        accesoTirolesa.lock();
        cantTirolesas++;
        System.out.println("La persona " + Thread.currentThread().getName()
                + " se bajó de la tirolesa de ESTE a OESTE, tirolesas disponibles: " + cantTirolesas);

        if (cantEsperandoLado2 > 0) {
            System.out.println("entra");
            colaTirolesa2.signalAll();
        } else { 
            System.out.println("LADO OESTE VACIO");
            semControl.release();
        }
        accesoTirolesa.unlock();
    }

    public void bajarseTirolesa2() {
        accesoTirolesa.lock();
        cantTirolesas++;
        System.out.println("La persona " + Thread.currentThread().getName()
                + " se bajó de la tirolesa de OESTE a ESTE, tirolesas disponibles: " + cantTirolesas);

        if (cantEsperandoLado1 > 0) {
            colaTirolesa1.signalAll();
        } else {
            System.out.println("LADO ESTE VACIO");
            semControl.release();
        }
        accesoTirolesa.unlock();
    }

    
    public void verficarTirolesa() {  //metodo del control de la tirolesa
        try {
            semControl.acquire();
            System.out.println("paso");
            if(cantEsperandoLado1 > 0){
                System.out.println("TIROLESA VA DE ESTE A OESTE");
            }else{
                System.out.println("TIROLESA VA DE OESTE A ESTE");
            }

        } catch (InterruptedException e) {}

    }

    public void llevarTirolesa(){ //metodo del control de la tirolesa
        accesoTirolesa.lock();
        cantTirolesas++; 
        colaTirolesa1.signalAll();
        colaTirolesa2.signalAll();
        accesoTirolesa.unlock();
    }


    //CUERDA 

    public void usarCuerda(){
        accesoCuerda.lock();
            try {
                while(cuerda < 1){
                colaCuerda.await();
                }
                cuerda--; 
                System.out.println("La persona "+Thread.currentThread().getName()+" se encuentra usando la cuerda");
            } catch (InterruptedException e) {
            }finally{
                accesoCuerda.unlock();
            }   
    }

    public void dejarCuerda(){
        accesoCuerda.lock();
        System.out.println("La persona "+Thread.currentThread().getName()+" dejó de usar la cuerda");
        cuerda++; 
        colaCuerda.signalAll();
        accesoCuerda.unlock();
    }


    public void saltar(){
        accesoSalto.lock();
        try {
            while(cantSaltos == 0){
                colaSalto.await();
            }
            cantSaltos--; 
            System.out.println("La persona "+Thread.currentThread().getName()+" está por saltar");
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            accesoSalto.unlock();
        }
    }


    public void dejarSalto(){
        accesoSalto.lock();
        System.out.println("La persona "+Thread.currentThread().getName()+" acaba de saltar");
        cantSaltos++; 
        colaSalto.signalAll();
        accesoSalto.unlock();
    }


}
