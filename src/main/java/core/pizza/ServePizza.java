package core.pizza;

import core.*;
import core.control.Scheduler;
import core.payment.ServeCustomer;

public class ServePizza extends Event {

    private Device oven;
    private Customer customer;

    public ServePizza(Customer customer, Device oven, int startingTime){
        super(startingTime);
        this.oven = oven;
        this.customer = customer;
    }

    public void run(){
        oven.setFree(true);
        WaitingList.getInstance().searchPizza(customer);
        Scheduler.getInstance().addEvent(new ServeCustomer(customer, getStartingTime()));
    }

}
