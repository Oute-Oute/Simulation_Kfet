package main.java.ramen;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.control.ControllerDevices;
import main.java.control.Scheduler;

public class EndBoilingWater extends Event {

    private final Device kettle;
    private final Customer customer;

    public EndBoilingWater(Customer customer, Device kettle, int startingTime) {
        super(startingTime);
        this.kettle = kettle;
        this.customer = customer;
    }

    @Override
    public void run() {
        StartBoilingWater.setIsCold(false);
        StartBoilingWater.setLastBoiling(Scheduler.getInstance().getCurrentTime());
        this.kettle.setFree(true);
        ControllerDevices.getInstance().getFreeDevices().replace("Kettle", ControllerDevices.getInstance().getFreeDevices().get("Kettle") + 1);

        Scheduler.getInstance().addEvent(new ServeRamen(customer, getStartingTime() + 1));

    }
}
