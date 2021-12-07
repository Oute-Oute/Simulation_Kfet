package core.payment;

import core.Customer;
import core.Event;
import core.Kfetier;
import core.WaitingList;
import core.control.ControllerHR;
import core.control.Scheduler;

public class NewCustomer extends Event {

    private Customer customer;

    public NewCustomer(Customer customer, int startingTime){
        super(startingTime);
        this.customer = customer;
    }

    @Override
    public void run() {
        int time = 60; //le temps que met cet event à se réaliser

        //S'il y a un caissier de libre
        if(ControllerHR.getInstance().getFreeKfetier().get("Cashier") > 0) {

            int position = ControllerHR.getInstance().whichCashier();
            Kfetier cashier = ControllerHR.getInstance().getCashier().get(position);

            //On set le temps à attendre
            time += customer.getPaymentDuration();

            //On ajoute au Scheduler
            Scheduler.getInstance().addEvent(new EndPayment(customer, cashier, time));

        }
        else {
            //Le client est ajouté à la liste d'attente pré order
            WaitingList.getInstance().getPreOrder().add(customer);
        }
    }
}
