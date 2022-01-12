package main.java;

import classes.Customer;
import main.java.chocolate.PreparationChocolate;
import main.java.coffee.PreparationCoffee;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.PreparationOrder;
import main.java.picard.PreparationPicard;
import main.java.pizza.PreparationPizza;
import main.java.ramen.StartBoilingWater;

import java.util.ArrayList;

public final class WaitingList {

    private static WaitingList waitingListInstance = new WaitingList();
    private final ArrayList<Customer> preOrder;
    private final ArrayList<Customer> postOrder;

    private WaitingList() {
        preOrder = new ArrayList<>();
        postOrder = new ArrayList<>();
    }

    public static WaitingList getInstance() {
        if (waitingListInstance == null) {
            waitingListInstance = new WaitingList();
        }

        return waitingListInstance;
    }

    public ArrayList<Customer> getPreOrder() {
        return preOrder;
    }

    public ArrayList<Customer> getPostOrder() {
        return postOrder;
    }

    /**
     * Search what part of the customer's order can be prepared depending on the different free devices
     *
     * @param customer
     */
    public void searchGlobal(Customer customer) {
        System.out.println("Algo global customer " + customer.id);
        ControllerHR HRInstance = ControllerHR.getInstance();
        int time = Scheduler.getInstance().getCurrentTime() + 1;

        if (customer.getOrder().getChocolate() + customer.getOrder().getCoffee() + customer.getOrder().getRamen() + customer.getOrder().getPicard() > 0) {      //Il y a une commande à préparer

            if (HRInstance.getFreeKfetier().get("Kfetier") > 0) {           // Un kfetier est libre

                ControllerDevices devicesInstance = ControllerDevices.getInstance();
                boolean found = false;

                if (customer.getOrder().getPicard() > 0) {
                    System.out.println("Picard à faire");
                    if (devicesInstance.getFreeDevices().get("Microwave") > 0) {
                        found = true;
                        Scheduler.getInstance().addEvent(new PreparationPicard(customer, time));
                    }
                } else if (customer.getOrder().getRamen() > 0 && !found) {
                    System.out.println("Ramen à faire");
                    if (devicesInstance.getFreeDevices().get("Kettle") > 0) {
                        found = true;
                        Scheduler.getInstance().addEvent(new StartBoilingWater(customer, time));
                    }
                } else if (customer.getOrder().getCoffee() > 0 && !found) {
                    System.out.println("Café à faire");
                    if (devicesInstance.getFreeDevices().get("Cafetiere") > 0) {
                        found = true;
                        Scheduler.getInstance().addEvent(new PreparationCoffee(customer, time));
                    }
                } else if (customer.getOrder().getChocolate() > 0 && !found) {
                    System.out.println("Chocolat à faire");
                    if (devicesInstance.getFreeDevices().get("Cocoa") > 0) {
                        found = true;
                        Scheduler.getInstance().addEvent(new PreparationChocolate(customer, time));
                    }
                }
                if(!found){
                    System.out.println("Pas d'appareil libre");
                    //Scheduler.getInstance().addEvent(new PreparationOrder(customer, time));
                }
            } else {
                System.out.println("Pas de kfetier libre");
                Scheduler.getInstance().addEvent(new PreparationOrder(customer, time+49));
            }
        } else {
            System.out.println("Algo global: pas de commande à faire");
        }
    }

    /**
     * Search if the customer ordered a pizza and if an oven is free to start the PreparationPizza
     *
     * @param customer
     */
    public void searchPizza(Customer customer) {
        System.out.println("search pizza "+customer.id);
        ControllerDevices devicesInstance = ControllerDevices.getInstance();
        ControllerHR HRInstance = ControllerHR.getInstance();
        if (customer.getOrder().getNbPizza() > 0) {
            if (HRInstance.getFreeKfetier().get("Cook") > 0) {           // Un kfetier est libre
                if (devicesInstance.getFreeDevices().get("Oven") > 0) {
                    Scheduler.getInstance().addEvent(new PreparationPizza(customer, Scheduler.getInstance().getCurrentTime() + 1));
                }
            }
        }
    }
}
