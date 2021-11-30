package core.payment;

import core.Customer;
import core.Event;
import core.WaitingList;

public class preparationOrder extends Event {

    private Customer customer;

    public preparationOrder(Customer customer, int startingTime){
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
