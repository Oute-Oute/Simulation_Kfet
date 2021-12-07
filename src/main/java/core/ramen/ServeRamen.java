package core.ramen;

import core.Customer;
import core.Event;


public class ServeRamen extends Event {

    private Customer customer;

    public ServeRamen(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {

    }
}
