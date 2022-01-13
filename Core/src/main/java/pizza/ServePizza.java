package main.java.pizza;

import classes.Customer;

import kfet.CoreController;
import main.java.Device;
import main.java.Event;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;
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
        if (ControllerHR.getInstance().getFreeKfetier().get("Cook") > 0) {
            int position = ControllerHR.getInstance().whichCook(oven.getId(),1);
            oven.setFree(true);
            ControllerDevices.getInstance().getFreeDevices().replace("Oven", ControllerDevices.getInstance().getFreeDevices().get("Oven") + 1);
            WaitingList.getInstance().searchPizza(customer);
            Scheduler.getInstance().addEvent(new ServeCustomer(customer, getStartingTime() + 1));
            ControllerHR.getInstance().getCooks().get(position).setFree(true);
            ControllerHR.getInstance().getFreeKfetier().replace("Cook", ControllerHR.getInstance().getFreeKfetier().get("Cook") + 1);
        }
        else {                    //Pas de kfetier libre
            Scheduler.getInstance().addEvent(new ServePizza(customer,oven, Scheduler.getInstance().getCurrentTime() + 60));
        }
    }

}
