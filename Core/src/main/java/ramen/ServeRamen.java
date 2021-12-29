package main.java.ramen;

import classes.Customer;

import main.java.Event;


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
