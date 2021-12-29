package main.java.payment;

import classes.Customer;

import main.java.Event;
import main.java.WaitingList;

public class PreparationOrder extends Event {

    private Customer customer;

    public PreparationOrder(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        //On ajoute le client à la liste d'attente post order
        WaitingList.getInstance().getPostOrder().add(customer);

        //on cherche si quelque chose est dispo pour lancer sa commande, sinon l'algo le trouvera plus tard
        WaitingList.getInstance().searchGlobal(customer);
        WaitingList.getInstance().searchPizza(customer);
    }
}
