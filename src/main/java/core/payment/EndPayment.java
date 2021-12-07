package core.payment;

import core.Customer;
import core.Event;
import core.Kfetier;
import core.WaitingList;
import core.control.ControllerHR;
import core.control.Scheduler;

public class EndPayment extends Event {

    private Customer customer;
    private Kfetier cashier;

    public EndPayment(Customer customer, Kfetier cashier, int StartingTime){
        super(StartingTime);
        this.customer = customer;
        this.cashier = cashier;
    }

    @Override
    public void run() {
        //Libère le caissier
        cashier.setFree(true);
        ControllerHR.getInstance().getFreeKfetier().replace("Cashier", ControllerHR.getInstance().getFreeKfetier().get("Cashier") + 1);

        //Lance la préparation de commande
        Scheduler.getInstance().addEvent(new PreparationOrder(customer, Scheduler.getInstance().getCurrentTime()));

        //Retire le client de la pré order
        WaitingList.getInstance().getPreOrder().remove(customer);

        //Si la liste Pré Order n'est pas vide, on appelle le client suivant
        if( !WaitingList.getInstance().getPreOrder().isEmpty() ){
            Scheduler.getInstance().addEvent(new NewCustomer(WaitingList.getInstance().getPreOrder().get(0), Scheduler.getInstance().getCurrentTime()));
        }
    }
}
