package core.payment;

import core.Customer;
import core.Event;
import core.WaitingList;

public class ServeCustomer extends Event {

    private Customer customer;

    public ServeCustomer(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    public void run(){

        int nbOrder = customer.getOrder().getNbPizza() +
                customer.getOrder().getPicard() +
                customer.getOrder().getChocolate() +
                customer.getOrder().getCoffee() +
                customer.getOrder().getRamen();
        if( nbOrder == 0){
            WaitingList.getInstance().getPostOrder().remove(customer);
        }

    }
}
