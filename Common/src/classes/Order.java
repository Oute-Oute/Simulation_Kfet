package classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Order.
 */
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID =1L;
    private static final int MIN_COOKINGTIME_PIZZA = 900, MAX_COOKINGTIME_PIZZA = 1200, AVERAGE_COOKINGTIME_PIZZA = 1020;
    private final int max_order;
    private final int nbArticles;
    private int nbServed;
    private int coffee;
    private int chocolate;
    private int ramen;
    private int picard;
    private int nbPizza;
    private ArrayList<Integer> pizza;

    /**
     * Instantiates a new Order.
     *
     * @param max the max of each articles you can order
     */
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

    /**
     * Gets the number of coffee.
     *
     * @return the number of coffee
     */
    public int getCoffee() {
        return coffee;
    }

    /**
     * Sets the number of coffee.
     *
     * @param coffee the number of coffee
     */
    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    /**
     * Gets the number of chocolate.
     *
     * @return the number of chocolate
     */
    public int getChocolate() {
        return chocolate;
    }

    /**
     * Sets the number of chocolate.
     *
     * @param chocolate the number of chocolate
     */
    public void setChocolate(int chocolate) {
        this.chocolate = chocolate;
    }

    /**
     * Gets the number of ramen.
     *
     * @return the number of ramen
     */
    public int getRamen() {
        return ramen;
    }

    /**
     * Sets the number of ramen.
     *
     * @param ramen the number of ramen
     */
    public void setRamen(int ramen) {
        this.ramen = ramen;
    }

    /**
     * Gets the number of picard.
     *
     * @return the number of picard
     */
    public int getPicard() {
        return picard;
    }

    /**
     * Sets the number of picard.
     *
     * @param picard the number of picard
     */
    public void setPicard(int picard) {
        this.picard = picard;
    }

    /**
     * Gets nb pizza.
     *
     * @return the nb pizza
     */
    public int getNbPizza() {
        return nbPizza;
    }

    /**
     * Sets the number of pizza.
     *
     * @param nbPizza the nb pizza
     */
    public void setNbPizza(int nbPizza) {
        this.nbPizza = nbPizza;
    }

    /**
     * Gets the list of pizza.
     *
     * @return the list of pizza
     */
    public ArrayList<Integer> getPizza() {
        return pizza;
    }

    /**
     * Gets the number of articles.
     *
     * @return the number of articles
     */
    public int getNbArticles() {
        return nbArticles;
    }

    /**
     * Gets the number of artcles served.
     *
     * @return the number of articles served
     */
    public int getNbServed() {
        return nbServed;
    }

    /**
     * Sets the number of articles served.
     *
     * @param nbServed the number of articles served
     */
    public void setNbServed(int nbServed) {
        this.nbServed = nbServed;
    }
}
