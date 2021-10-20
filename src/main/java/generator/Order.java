package generator;

import java.util.ArrayList;
import java.util.Random;

public class Order {

    private static final int MAX_ORDER = 5;

    private int coffee;
    private int chocolate;
    private int ramen;
    private int picard;
    private int nbPizza;
    private ArrayList<Integer> pizza;

    public Order(){
        Random r = new Random();
        coffee = r.nextInt(MAX_ORDER);
        chocolate = r.nextInt(MAX_ORDER);
        ramen = r.nextInt(MAX_ORDER);
        picard = r.nextInt(MAX_ORDER);
        nbPizza = r.nextInt(MAX_ORDER);

        pizza = new ArrayList<Integer>();

        for(int i=0; i<nbPizza; i++){
            pizza.add(150*(int)r.nextGaussian() + 1050);
        }
    }

    public int getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    public int getChocolate() {
        return chocolate;
    }

    public void setChocolate(int chocolate) {
        this.chocolate = chocolate;
    }

    public int getRamen() {
        return ramen;
    }

    public void setRamen(int ramen) {
        this.ramen = ramen;
    }

    public int getPicard() {
        return picard;
    }

    public void setPicard(int picard) {
        this.picard = picard;
    }

    public int getNbPizza() {
        return nbPizza;
    }

    public void setNbPizza(int nbPizza) {
        this.nbPizza = nbPizza;
    }

    public ArrayList<Integer> getPizza() {
        return pizza;
    }

}
