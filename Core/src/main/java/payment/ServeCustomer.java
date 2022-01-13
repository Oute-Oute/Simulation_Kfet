package main.java.payment;

import classes.Customer;

import main.java.Event;
import main.java.WaitingList;
import main.java.control.Scheduler;


public class ServeCustomer extends Event {

    private final Customer customer;

    public ServeCustomer(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    public void run() {

        customer.getOrder().setNbServed(customer.getOrder().getNbServed() + 1);

        if (customer.getOrder().getNbArticles() == customer.getOrder().getNbServed()) {
            customer.setDepartureTime(Scheduler.getInstance().getCurrentTime());
            WaitingList.getInstance().getPostOrder().remove(customer);
        }

        if (Scheduler.getInstance().getNbEvent() == 1) {
            if (WaitingList.getInstance().getPostOrder().size() == 0) {
                Scheduler.getInstance().setCurrentTime(7200);
            }
            for (int i = 0; i < WaitingList.getInstance().getPostOrder().size(); i++) {
                Customer customer1 = WaitingList.getInstance().getPostOrder().get(i);

                if (customer1.getOrder().getNbPizza() > 0) {
                    WaitingList.getInstance().searchPizza(customer1);
                }

                if (customer1.getOrder().getChocolate() + customer1.getOrder().getCoffee() + customer1.getOrder().getRamen() + customer1.getOrder().getPicard() > 0) {
                    WaitingList.getInstance().searchGlobal(customer1);
                } else if (customer1.getOrder().getNbArticles() == 0 && customer1.getOrder().getNbServed() == 0) {
                    customer1.setDepartureTime(Scheduler.getInstance().getCurrentTime());
                    WaitingList.getInstance().getPostOrder().remove(customer1);
                }
            }
        }
    }
}