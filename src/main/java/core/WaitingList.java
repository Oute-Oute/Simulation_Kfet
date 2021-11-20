package core;

import java.util.ArrayList;

public class WaitingList {
    private ArrayList<Customer> preOrder;
    private ArrayList<Customer> postOrder;

    public WaitingList() {
        preOrder = new ArrayList<>();
        postOrder = new ArrayList<>();
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
        ControllerDevices devices = new ControllerDevices();
        boolean found = false;

        if(customer.getOrder().getPicard() > 0){
            if (devices.getFreeDevices().get("Microwave") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
        else if(customer.getOrder().getRamen() > 0 && !found){
            if (devices.getFreeDevices().get("Kettle") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
        else if(customer.getOrder().getCoffee() > 0 && !found){
            if (devices.getFreeDevices().get("Cafetiere") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
        else if(customer.getOrder().getChocolate() > 0 && !found){
            if (devices.getFreeDevices().get("Cocoa") > 0){
                found = true;
                //TODO: appeler debut commande avec paramètre client
            }
        }
    }

    /**
     * Search if the customer ordered a pizza and if an oven is free to start the preparation
     * @param customer
     */
    public void searchPizza(Customer customer) {
        ControllerDevices devices = new ControllerDevices();

        if(customer.getOrder().getNbPizza() > 0){
            if(devices.getFreeDevices().get("Oven") > 0){
                //TODO: appeler debut commande avec paramètre client
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
