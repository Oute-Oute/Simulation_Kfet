package main.java.payment;

import classes.Customer;

import main.java.Event;
import main.java.WaitingList;
import main.java.control.Scheduler;


public class ServeCustomer extends Event {

    private Customer customer;

    public ServeCustomer(Customer customer, int startingTime) {
        super(startingTime);
        this.customer = customer;
    }

    public void run() {
        System.out.println("Serve Customer " + customer.id);

        customer.getOrder().setNbServed(customer.getOrder().getNbServed() + 1);

        if (customer.getOrder().getNbArticles() == customer.getOrder().getNbServed()) {
            customer.setDepartureTime(Scheduler.getInstance().getCurrentTime());
            System.out.println("Customer arrived at " + customer.getArrivalTime() + " and departed at " + customer.getDepartureTime());
            int position = WaitingList.getInstance().getPostOrder().indexOf(customer);
            WaitingList.getInstance().getPostOrder().remove(position);
            System.out.println("size = "+ WaitingList.getInstance().getPostOrder().size()+"position = "+position);
        }

        if (Scheduler.getInstance().getnbEvent() == 1) {
            System.out.println("\t\tVerification de fin & size = "+ WaitingList.getInstance().getPostOrder().size());
            for (int i = 0; i < WaitingList.getInstance().getPostOrder().size(); i++) {
                Customer customer1 = WaitingList.getInstance().getPostOrder().get(i);

                if (customer1.getOrder().getNbPizza() > 0) {
                    WaitingList.getInstance().searchPizza(customer1);
                }

                if (customer1.getOrder().getChocolate() + customer1.getOrder().getCoffee() + customer1.getOrder().getRamen() + customer1.getOrder().getPicard() > 0) {
                    WaitingList.getInstance().searchGlobal(customer1);
                }
            }
        }

    }
}