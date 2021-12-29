package main.java.chocolate;

import classes.Customer;

import main.java.Device;
import main.java.Kfetier;
import main.java.Event;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;


public class PreparationChocolate extends Event {

    private Customer customer;

    public PreparationChocolate(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        int position = ControllerHR.getInstance().whichKfetier();
        Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);
        position = ControllerDevices.getInstance().whichCocoa();
        Device cocoa = ControllerDevices.getInstance().getCocoa().get(position);

        customer.getOrder().setChocolate(customer.getOrder().getChocolate() - 1);

        Scheduler.getInstance().addEvent(new ServeChocolate(customer, cocoa, kfetier, getStartingTime() + 30 ));

    }
}
