package main.java.picard;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.Scheduler;


public class CookingPicard extends Event {

    private Kfetier kfetier;
    private Device microwave;
    private Customer customer;
    private int cooked;

    public CookingPicard(Customer customer, Kfetier kfetier, Device microwave, int cooked, int startingTime){
        super(startingTime);
        this.kfetier = kfetier;
        this.microwave = microwave;
        this.customer = customer;
        this.cooked = cooked;
    }

    public void run(){
        System.out.println("Cooking Picard");
        kfetier.setFree(true);
        WaitingList.getInstance().searchGlobal(customer);
        Scheduler.getInstance().addEvent(new ServePicard(customer, microwave, 1, getStartingTime()));
    }

}
