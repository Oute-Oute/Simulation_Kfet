package main.java.payment;

import classes.Customer;

import kfet.CoreController;
import main.java.Event;
import main.java.Kfetier;
import main.java.WaitingList;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;


public class EndPayment extends Event {

    private final Customer customer;
    private final Kfetier cashier;

    public EndPayment(Customer customer, Kfetier cashier, int StartingTime) {
        super(StartingTime);
        this.customer = customer;
        this.cashier = cashier;
    }

    @Override
    public void run() {
        //Libère le caissier
        System.out.println("End Payment "+ customer.id);
        cashier.setFree(true);
        CoreController.getInstance().free(cashier.getId());
        ControllerHR.getInstance().getFreeKfetier().replace("Cashier", ControllerHR.getInstance().getFreeKfetier().get("Cashier") + 1);

        int time = Scheduler.getInstance().getCurrentTime() + 1;

        //Lance la préparation de commande
        Scheduler.getInstance().addEvent(new PreparationOrder(customer, time));


        //Si la liste Pré Order n'est pas vide, on appelle le client suivant
        if (!WaitingList.getInstance().getPreOrder().isEmpty()) {
            System.out.println("Appel client suivant de préOrder: Client "+WaitingList.getInstance().getPreOrder().get(0).id);
            Scheduler.getInstance().addEvent(new NewCustomer(WaitingList.getInstance().getPreOrder().get(0), time));
        }
    }
}
