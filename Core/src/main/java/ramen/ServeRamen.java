package main.java.ramen;

import classes.Customer;

import main.java.Event;
import main.java.WaitingList;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;


public class ServeRamen extends Event {

    private Customer customer;

    public ServeRamen(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        System.out.println("Serve Ramen");
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime() + 1));
        WaitingList.getInstance().searchGlobal(customer);
    }
}
