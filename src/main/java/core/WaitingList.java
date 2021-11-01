package core;

import java.util.ArrayList;

public class WaitingList {
    private ArrayList<Customer> preOrder;
    private ArrayList<Customer> postOrder;

    public WaitingList() {

    }

    public ArrayList<Customer> getPreOrder() {
        return preOrder;
    }

    public ArrayList<Customer> getPostOrder() {
        return postOrder;
    }

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
        else {

        }
    }

    public void searchPizza(Customer customer) {
        ControllerDevices devices = new ControllerDevices();

        if(customer.getOrder().getNbPizza() > 0){
            if(devices.getFreeDevices().get("Oven") > 0){
                //TODO: appeler debut commande avec paramètre client
            }
        }
    }

}
