package core.ramen;

import core.*;
import core.control.ControllerDevices;
import core.control.ControllerHR;
import core.control.Scheduler;

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
        if(isCold){
            int position = ControllerHR.getInstance().whichKfetier();
            Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);
            position = ControllerDevices.getInstance().whichKettle();
            Device kettle = ControllerDevices.getInstance().getKettle().get(position);

            Scheduler.getInstance().addEvent(new EndBoilingWater(customer, kettle, getStartingTime() + 180));
            WaitingList.getInstance().searchGlobal(customer);


        } else {
            Scheduler.getInstance().addEvent(new ServeRamen(customer, getStartingTime() + 1 ));
        }

    }
}
