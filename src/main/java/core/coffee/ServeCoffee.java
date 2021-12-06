package core.coffee;

import core.*;
import core.control.Scheduler;
import core.payment.serveCustomer;

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
        Scheduler.getInstance().addEvent(new serveCustomer(customer, Scheduler.getInstance().getCurrentTime()));


    }
}
