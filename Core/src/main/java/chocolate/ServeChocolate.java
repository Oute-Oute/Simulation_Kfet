package main.java.chocolate;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;


public class ServeChocolate extends Event {

    private Customer customer;
    private Device cocoa;
    private Kfetier kfetier;

    public ServeChocolate(Customer customer, Device cocoa, Kfetier kfetier, int startingTime) {
        super(startingTime);
        this.cocoa = cocoa;
        this.customer = customer;
        this.kfetier = kfetier;
    }

    @Override
    public void run() {
        System.out.println("Serve chocolate");
        kfetier.setFree(true);
        ControllerHR.getInstance().getFreeKfetier().replace("Kfetier",ControllerHR.getInstance().getFreeKfetier().get("Kfetier") + 1);

        cocoa.setFree(true);
        ControllerDevices.getInstance().getFreeDevices().replace("Cocoa", ControllerDevices.getInstance().getFreeDevices().get("Cocoa") + 1);

        WaitingList.getInstance().searchGlobal(customer);
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime() + 1));


    }
}
