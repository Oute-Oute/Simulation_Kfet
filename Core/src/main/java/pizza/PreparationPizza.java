package main.java.pizza;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;

public class PreparationPizza extends Event {
    private Customer customer;

    public PreparationPizza(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        System.out.println("Preparation Pizza " + customer.id);
        if (ControllerHR.getInstance().getFreeKfetier().get("Cook") > 0) {
            int time = 30; //le temps que met cet event à se réaliser

            //On récupere le Cuisinier et le four qui vont mettre la pizza à cuire
            int position = ControllerHR.getInstance().whichCook();
            Kfetier cook = ControllerHR.getInstance().getCooks().get(position);
            position = ControllerDevices.getInstance().whichOven();
            Device oven = ControllerDevices.getInstance().getOven().get(position);

            //On change le nb de pizza dans la commande
            customer.getOrder().setNbPizza(customer.getOrder().getNbPizza() - 1);

            //On set le temps à attendre
            time += Scheduler.getInstance().getCurrentTime();

            //On ajoute au Scheduler
            Scheduler.getInstance().addEvent(new CookingPizza(customer, cook, oven, time));
        } else {
            System.out.println("Pas de cuisinier de libre");
            Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime() + 60));
        }
    }
}

