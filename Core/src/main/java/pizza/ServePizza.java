package main.java.pizza;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;


public class ServePizza extends Event {

    private Device oven;
    private Customer customer;

    public ServePizza(Customer customer, Device oven, int startingTime) {
        super(startingTime);
        this.oven = oven;
        this.customer = customer;
    }

    public void run() {
        System.out.println("Serve Pizza " + customer.id);
        oven.setFree(true);
        ControllerDevices.getInstance().getFreeDevices().replace("Oven", ControllerDevices.getInstance().getFreeDevices().get("Oven") + 1);
        WaitingList.getInstance().searchPizza(customer);
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, getStartingTime() + 1));
    }

}
