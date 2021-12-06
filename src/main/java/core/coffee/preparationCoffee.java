package core.coffee;

import core.Customer;
import core.Device;
import core.Event;
import core.Kfetier;
import core.control.ControllerDevices;
import core.control.ControllerHR;
import core.control.Scheduler;

public class preparationCoffee extends Event {

    private Customer customer;

    public preparationCoffee(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        int position = ControllerHR.getInstance().whichKfetier();
        Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);
        position = ControllerDevices.getInstance().whichCafetiere();
        Device cafetiere = ControllerDevices.getInstance().getCafetiere().get(position);

        customer.getOrder().setCoffee(customer.getOrder().getCoffee() - 1);

        Scheduler.getInstance().addEvent(new ServeCoffee(customer, cafetiere, kfetier, getStartingTime() + 30 ));

    }
}
