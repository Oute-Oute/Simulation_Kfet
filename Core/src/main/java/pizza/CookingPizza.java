package main.java.pizza;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;


public class CookingPizza extends Event {

    private Kfetier cook;
    private Device oven;
    private Customer customer;

    public CookingPizza(Customer customer, Kfetier cook, Device oven, int startingTime) {
        super(startingTime);
        this.cook = cook;
        this.oven = oven;
        this.customer = customer;
    }

    public void run() {
        System.out.println("Cooking Pizza " + customer.id);
        cook.setFree(true);
        ControllerHR.getInstance().getFreeKfetier().replace("Cook", ControllerHR.getInstance().getFreeKfetier().get("Cook") + 1);
        int time = customer.getOrder().getPizza().get(0) + getStartingTime();
        WaitingList.getInstance().searchPizza(customer);
        Scheduler.getInstance().addEvent(new ServePizza(customer, oven, time));
    }

}
