package core.chocolate;

import core.*;
import core.control.Scheduler;
import core.payment.ServeCustomer;

public class ServeChocolate extends Event {

    private Customer customer;
    private Device cocoa;
    private Kfetier kfetier;

    public ServeChocolate(Customer customer, Device cocoa, Kfetier kfetier, int startingTime){
        super(startingTime);
        this.cocoa = cocoa;
        this.customer = customer;
        this.kfetier = kfetier;
    }

    @Override
    public void run() {
        kfetier.setFree(true);
        cocoa.setFree(true);

        WaitingList.getInstance().searchGlobal(customer);
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime()));


    }
}
