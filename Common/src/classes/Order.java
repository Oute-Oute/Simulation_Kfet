package classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID =1L;
    private static final int MIN_COOKINGTIME_PIZZA = 900, MAX_COOKINGTIME_PIZZA = 1200, AVERAGE_COOKINGTIME_PIZZA = 1020;
    private final int max_order;
    private final int nbArticles;
    private int coffee;
    private int chocolate;
    private int ramen;
    private int picard;
    private int nbPizza;
    private ArrayList<Integer> pizza;

    public Order(int max){
        Random r = new Random();
        max_order=max;
        coffee = r.nextInt(max_order);
        chocolate = r.nextInt(max_order);
        ramen = r.nextInt(max_order);
        picard = r.nextInt(max_order);
        nbPizza = r.nextInt(max_order);


        nbArticles = coffee + chocolate + ramen + picard + nbPizza;
        pizza = new ArrayList<>();

        for(int i=0; i<nbPizza; i++){
            pizza.add( ((MAX_COOKINGTIME_PIZZA-MIN_COOKINGTIME_PIZZA)/2) * (int)r.nextGaussian() + AVERAGE_COOKINGTIME_PIZZA );
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

    public int getNbArticles() {
        return nbArticles;
    }
}
