package core.ramen;

import core.Customer;
import core.Device;
import core.Event;
import core.control.Scheduler;

public class EndBoilingWater extends Event {

    private Device kettle;
    private Customer customer;

    public EndBoilingWater(Customer customer, Device kettle, int startingTime){
        super(startingTime);
        this.kettle = kettle;
        this.customer = customer;
    }

    @Override
    public void run() {
        StartBoilingWater.setIsCold(false);
        kettle.setFree(true);

        Scheduler.getInstance().addEvent(new ServeRamen(customer, getStartingTime() + 1 ));

    }
}
