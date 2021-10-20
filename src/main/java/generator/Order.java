package generator;

import java.util.ArrayList;

public class Order {
    private int coffee;
    private int chocolate;
    private int ramen;
    private int picard;
    private int nbPizza;
    private ArrayList<Integer> pizza;

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
