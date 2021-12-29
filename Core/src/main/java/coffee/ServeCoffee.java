package main.java.coffee;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;


public class ServeCoffee extends Event {

    private Customer customer;
    private Device cafetiere;
    private Kfetier kfetier;

    public ServeCoffee(Customer customer, Device cafetiere, Kfetier kfetier, int startingTime){
        super(startingTime);
        this.cafetiere = cafetiere;
        this.customer = customer;
        this.kfetier = kfetier;
    }

    @Override
    public void run() {
        kfetier.setFree(true);
        cafetiere.setFree(true);

        WaitingList.getInstance().searchGlobal(customer);
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime()));


    }
}
