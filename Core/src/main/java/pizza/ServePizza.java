package main.java.pizza;

import classes.Customer;

import kfet.CoreController;
import main.java.Device;
import main.java.Event;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;


public class ServePizza extends Event {

    private final Device oven;
    private final Customer customer;

    public ServePizza(Customer customer, Device oven, int startingTime) {
        super(startingTime);
        this.oven = oven;
        this.customer = customer;
    }

    public void run() {
        if (ControllerHR.getInstance().getFreeKfetier().size() > 0) {
            int position = ControllerHR.getInstance().whichCook(oven.getId());
            oven.setFree(true);
            ControllerDevices.getInstance().getFreeDevices().replace("Oven", ControllerDevices.getInstance().getFreeDevices().get("Oven") + 1);
            WaitingList.getInstance().searchPizza(customer);
            Scheduler.getInstance().addEvent(new ServeCustomer(customer, getStartingTime() + 1));
            ControllerHR.getInstance().getCooks().get(position).setFree(true);
        }
    }

}
