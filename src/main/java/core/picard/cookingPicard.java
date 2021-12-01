package core.picard;

import core.*;
import core.control.scheduler;

public class cookingPicard extends Event {

    private Kfetier kfetier;
    private Device microwave;
    private Customer customer;

    public cookingPicard(Customer customer, Kfetier kfetier, Device microwave, int startingTime){
        super(startingTime);
        this.kfetier = kfetier;
        this.microwave = microwave;
        this.customer = customer;
    }

    public void run(){
        kfetier.setFree(true);
        WaitingList.getInstance().searchGlobal(customer);
        scheduler.getInstance().addEvent(new servePicard(customer, microwave, getStartingTime()));  //TODO: gérer cas 2e cuisson
    }

}
