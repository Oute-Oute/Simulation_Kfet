package core.pizza;

import core.*;
import core.control.scheduler;

public class cookingPizza extends Event {

    private Kfetier cook;
    private Device oven;
    private Customer customer;

    public cookingPizza(Customer customer, Kfetier cook, Device oven, int startingTime){
        super(startingTime);
        this.cook= cook;
        this.oven = oven;
        this.customer = customer;
    }

    public void run(){
        cook.setFree(true);
        int time = customer.getOrder().getPizza().get(0) + getStartingTime();
        WaitingList.getInstance().searchPizza(customer);
        scheduler.getInstance().addEvent(new servePizza(customer, oven, time));
    }

}
