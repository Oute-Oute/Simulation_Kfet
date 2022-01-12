package main.java.picard;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.Kfetier;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;

public class PreparationPicard extends Event {
    private final Customer customer;

    public PreparationPicard(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        System.out.println("Preparation Picard " + customer.id);
        if (ControllerHR.getInstance().getFreeKfetier().get("Kfetier") > 0) {
            if (ControllerDevices.getInstance().getFreeDevices().get("Microwave")>0) {
                int time = 30; //le temps que met cet event à se réaliser

                //On récupere le Cuisinier et le four qui vont mettre la pizza à cuire

                int devicePosition = ControllerDevices.getInstance().whichMicrowave();
                Device microwave = ControllerDevices.getInstance().getMicrowave().get(devicePosition);
                int position = ControllerHR.getInstance().whichKfetier(devicePosition+8);
                Kfetier kfetier = ControllerHR.getInstance().getKfetiers().get(position);

                //On change le nb de picard dans la commande
                customer.getOrder().setPicard(customer.getOrder().getPicard() - 1);

                //On set le temps à attendre
                time += Scheduler.getInstance().getCurrentTime();

                //On ajoute au Scheduler
                Scheduler.getInstance().addEvent(new CookingPicard(customer, kfetier, microwave, 0, time));
            }
            else{
                System.out.println("Pas de micro onde libre");
                Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime() + 60));
            }
        } else {
            System.out.println("Pas de kfetier libre");
            Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime() + 60));
        }
    }
}

