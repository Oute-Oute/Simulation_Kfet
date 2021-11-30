package core;

import core.control.ControllerDevices;
import core.control.scheduler;
import core.pizza.preparationPizza;

import java.util.ArrayList;

public final class WaitingList {

    private static WaitingList waitingListInstance = new WaitingList();
    private ArrayList<Customer> preOrder;
    private ArrayList<Customer> postOrder;

    private WaitingList() {
        preOrder = new ArrayList<>();
        postOrder = new ArrayList<>();
    }

    public static WaitingList getInstance(){
        if(waitingListInstance == null){
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
     * @param customer
     */
    public void searchGlobal(Customer customer){
        ControllerDevices devicesInstance = ControllerDevices.getInstance();
        boolean found = false;

        if(customer.getOrder().getPicard() > 0){
            if (devicesInstance.getFreeDevices().get("Microwave") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
        else if(customer.getOrder().getRamen() > 0 && !found){
            if (devicesInstance.getFreeDevices().get("Kettle") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
        else if(customer.getOrder().getCoffee() > 0 && !found){
            if (devicesInstance.getFreeDevices().get("Cafetiere") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
        else if(customer.getOrder().getChocolate() > 0 && !found){
            if (devicesInstance.getFreeDevices().get("Cocoa") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
    }

    /**
     * Search if the customer ordered a pizza and if an oven is free to start the preparationPizza
     * @param customer
     */
    public void searchPizza(Customer customer) {
        ControllerDevices devicesInstance = ControllerDevices.getInstance();

        if(customer.getOrder().getNbPizza() > 0){
            if(devicesInstance.getFreeDevices().get("Oven") > 0){
                scheduler.getInstance().addEvent(new preparationPizza(customer, scheduler.getInstance().getCurrentTime()));
            }
        }
    }

    /**
     * Move a customer from preOrder to postOrder after his order has been taken
     */
    public void orderTaken(){
        postOrder.add(preOrder.get(0));
        preOrder.remove(0);
    }

    /**
     * Remove a customer from the waiting list when his order is done.
     * @param customer customer to remove
     */
    public void orderFinished(Customer customer){
        postOrder.remove(customer);
    }
}
