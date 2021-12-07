package core.picard;

import core.*;
import core.control.Scheduler;
import core.payment.ServeCustomer;

import java.util.Random;

public class ServePicard extends Event {

    private Device microwave;
    private Customer customer;
    private int cooked;     //0 si pas cuit, 1 si cuit une fois, 2 si cuit 2 fois

    public ServePicard(Customer customer, Device oven, int cooked, int startingTime){
        super(startingTime);
        this.microwave = oven;
        this.customer = customer;
        this.cooked = cooked;
    }

    public void run(){
        Random r = new Random();
        if(r.nextDouble() < 0.2 && cooked != 2) {
            Scheduler.getInstance().addEvent(new ServePicard(customer, microwave, 2, getStartingTime() + 150));
        } else {
            microwave.setFree(true);
            customer.getOrder().setPicard(customer.getOrder().getPicard() - 1);
            WaitingList.getInstance().searchGlobal(customer);
            Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime()));
        }
    }

}
