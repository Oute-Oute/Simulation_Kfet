package main.java.ramen;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;


public class StartBoilingWater extends Event {

    private static Boolean isCold = true;
    private Customer customer;

    public StartBoilingWater(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    public static void setIsCold(boolean cold) {
        isCold = cold;
    }

    @Override
    public void run() {
        System.out.println("Start boiling water");
        if(isCold){
            int position = ControllerDevices.getInstance().whichKettle();
            Device kettle = ControllerDevices.getInstance().getKettle().get(position);
            Scheduler.getInstance().addEvent(new EndBoilingWater(customer, kettle, getStartingTime() + 180));
            customer.getOrder().setRamen(customer.getOrder().getRamen() - 1);
            WaitingList.getInstance().searchGlobal(customer);
        } else {
            Scheduler.getInstance().addEvent(new ServeRamen(customer, getStartingTime() + 1 ));
            customer.getOrder().setRamen(customer.getOrder().getRamen() - 1);
        }


    }
}
