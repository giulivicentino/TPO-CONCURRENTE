package ECOPARQUE;

public class Persona extends Thread{
    private NadoDelfines pile; 
    private int horarioPile; 

    public Persona(String n, NadoDelfines p, int h){
        this.pile = p; 
        this.horarioPile = h; 
        this.setName(n);
    } 

    public void run(){
            pile.solPile(); 
    }

}
