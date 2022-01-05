package main.java.coffee;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;

public class PreparationCoffee extends Event {

    private Customer customer;

    public PreparationCoffee(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        System.out.println("Preparation Coffee "+customer.id);

        int position = ControllerHR.getInstance().whichKfetier();
        Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);
        position = ControllerDevices.getInstance().whichCafetiere();
        Device cafetiere = ControllerDevices.getInstance().getCafetiere().get(position);

        customer.getOrder().setCoffee(customer.getOrder().getCoffee() - 1);

        Scheduler.getInstance().addEvent(new ServeCoffee(customer, cafetiere, kfetier, Scheduler.getInstance().getCurrentTime() + 15 ));

    }
}
