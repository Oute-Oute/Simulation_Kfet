package main.java.payment;

import classes.Customer;

import main.java.Event;
import main.java.WaitingList;

public class PreparationOrder extends Event {

    private final Customer customer;

    public PreparationOrder(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        System.out.println("Preparation Order " + customer.id);

        if(!WaitingList.getInstance().getPostOrder().contains(customer)) {
            //On ajoute le client à la liste d'attente post order
            WaitingList.getInstance().getPostOrder().add(customer);
        }

        //on cherche si quelque chose est dispo pour lancer sa commande, sinon l'algo le trouvera plus tard
        if (customer.getOrder().getNbPizza() > 0) {
            WaitingList.getInstance().searchPizza(customer);
        }

        if (customer.getOrder().getChocolate() + customer.getOrder().getCoffee() + customer.getOrder().getRamen() + customer.getOrder().getPicard() > 0) {
            WaitingList.getInstance().searchGlobal(customer);
        }
    }
}
