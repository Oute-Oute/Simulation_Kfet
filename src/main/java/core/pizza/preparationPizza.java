package core.pizza;

import core.Customer;
import core.Device;
import core.Event;
import core.Kfetier;
import core.control.ControllerDevices;
import core.control.ControllerHR;
import core.control.Scheduler;

public class preparationPizza extends Event {
    private Customer customer;

    public preparationPizza(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
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
        Scheduler.getInstance().addEvent(new cookingPizza(customer, cook, oven, time));

    }
}

