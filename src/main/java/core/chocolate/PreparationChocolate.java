package core.chocolate;

import core.Customer;
import core.Device;
import core.Event;
import core.Kfetier;
import core.control.ControllerDevices;
import core.control.ControllerHR;
import core.control.Scheduler;

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
