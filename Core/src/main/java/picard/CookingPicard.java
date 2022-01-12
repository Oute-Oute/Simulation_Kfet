package main.java.picard;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;


public class CookingPicard extends Event {

    private final Kfetier kfetier;
    private final Device microwave;
    private final Customer customer;
    private final int cooked;

    public CookingPicard(Customer customer, Kfetier kfetier, Device microwave, int cooked, int startingTime){
        super(startingTime);
        this.kfetier = kfetier;
        this.microwave = microwave;
        this.customer = customer;
        this.cooked = cooked;
    }

    public void run(){
        System.out.println("Cooking Picard");
        int time = 360 + Scheduler.getInstance().getCurrentTime();

        kfetier.setFree(true);
        ControllerHR.getInstance().getFreeKfetier().replace("Kfetier",ControllerHR.getInstance().getFreeKfetier().get("Kfetier") + 1);

        WaitingList.getInstance().searchGlobal(customer);
        Scheduler.getInstance().addEvent(new ServePicard(customer, microwave, 1, time ));
    }
}
