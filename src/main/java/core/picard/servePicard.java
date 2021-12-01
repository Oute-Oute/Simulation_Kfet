package core.picard;

import core.*;
import core.control.scheduler;
import core.payment.serveCustomer;

public class servePicard extends Event {

    private Device microwave;
    private Customer customer;

    public servePicard(Customer customer, Device oven, int startingTime){
        super(startingTime);
        this.microwave = oven;
        this.customer = customer;
    }

    public void run(){
        microwave.setFree(true);
        WaitingList.getInstance().searchGlobal(customer);
        scheduler.getInstance().addEvent(new serveCustomer(customer, getStartingTime()));
    }

}
