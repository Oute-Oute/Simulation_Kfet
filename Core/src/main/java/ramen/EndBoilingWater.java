package main.java.ramen;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;

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
        if (ControllerHR.getInstance().getFreeKfetier().get("Kfetier") > 0) {
            int position = ControllerHR.getInstance().whichKfetier(kettle.getId(),1);
            StartBoilingWater.setIsCold(false);
            StartBoilingWater.setLastBoiling(Scheduler.getInstance().getCurrentTime());
            this.kettle.setFree(true);
            ControllerDevices.getInstance().getFreeDevices().replace("Kettle", ControllerDevices.getInstance().getFreeDevices().get("Kettle") + 1);

            Scheduler.getInstance().addEvent(new ServeRamen(customer, getStartingTime() + 1));

            ControllerHR.getInstance().getKfetiers().get(position).setFree(true);
            ControllerHR.getInstance().getFreeKfetier().replace("Kfetier", ControllerHR.getInstance().getFreeKfetier().get("Kfetier") + 1);
        }
        else {                    //Pas de kfetier libre
            Scheduler.getInstance().addEvent(new EndBoilingWater(customer,kettle, Scheduler.getInstance().getCurrentTime() + 60));
        }
    }
}
