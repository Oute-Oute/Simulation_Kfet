package main.java.coffee;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;

public class PreparationCoffee extends Event {

    private final Customer customer;

    public PreparationCoffee(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        if (ControllerHR.getInstance().getFreeKfetier().get("Kfetier") > 0) {

            int devicePosition = ControllerDevices.getInstance().whichCafetiere();
            Device cafetiere = ControllerDevices.getInstance().getCafetiere().get(devicePosition);
            int position = ControllerHR.getInstance().whichKfetier(devicePosition + 11);
            Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);

            customer.getOrder().setCoffee(customer.getOrder().getCoffee() - 1);

            Scheduler.getInstance().addEvent(new ServeCoffee(customer, cafetiere, kfetier, Scheduler.getInstance().getCurrentTime() + 30));
        } else {
            Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime() + 60));
        }
    }
}
