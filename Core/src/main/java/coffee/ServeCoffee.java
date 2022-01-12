package main.java.coffee;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;


public class ServeCoffee extends Event {

    private final Customer customer;
    private final Device cafetiere;
    private final Kfetier kfetier;

    public ServeCoffee(Customer customer, Device cafetiere, Kfetier kfetier, int startingTime) {
        super(startingTime);
        this.cafetiere = cafetiere;
        this.customer = customer;
        this.kfetier = kfetier;
    }

    @Override
    public void run() {
        System.out.println("Serve Coffee");
        kfetier.setFree(true);
        ControllerHR.getInstance().getFreeKfetier().replace("Kfetier",ControllerHR.getInstance().getFreeKfetier().get("Kfetier") + 1);

        cafetiere.setFree(true);
        ControllerDevices.getInstance().getFreeDevices().replace("Cafetiere", ControllerDevices.getInstance().getFreeDevices().get("Cafetiere") + 1);

        WaitingList.getInstance().searchGlobal(customer);
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime() + 1));


    }
}
