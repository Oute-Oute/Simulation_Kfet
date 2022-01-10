package main.java.chocolate;

import classes.Customer;

import main.java.Device;
import main.java.Kfetier;
import main.java.Event;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;


public class PreparationChocolate extends Event {

    private Customer customer;

    public PreparationChocolate(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        System.out.println("Preparation Chocolate");
        if (ControllerHR.getInstance().getFreeKfetier().get("Kfetier") > 0) {
            if (ControllerDevices.getInstance().getFreeDevices().get("Cocoa")>0) {
                int position = ControllerHR.getInstance().whichKfetier();
                Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);
                int devicePosition = ControllerDevices.getInstance().whichCocoa();
                Device cocoa = ControllerDevices.getInstance().getCocoa().get(devicePosition);

                customer.getOrder().setChocolate(customer.getOrder().getChocolate() - 1);

                Scheduler.getInstance().addEvent(new ServeChocolate(customer, cocoa, kfetier, getStartingTime() + 30));
            }
            else{
                System.out.println("Pas de chocolatiere libre");
                Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime() + 60));
            }
        } else {
            System.out.println("Pas de kfetier libre");
            Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime() + 60));
        }
    }
}
