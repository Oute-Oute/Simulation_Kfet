package main.java.picard;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;

import java.util.Random;

public class ServePicard extends Event {

    private Device microwave;
    private Customer customer;
    private int cooked;     //0 si pas cuit, 1 si cuit une fois, 2 si cuit 2 fois

    public ServePicard(Customer customer, Device oven, int cooked, int startingTime) {
        super(startingTime);
        this.microwave = oven;
        this.customer = customer;
        this.cooked = cooked;
    }

    public void run() {
        System.out.println("Serve Picard");
        Random r = new Random();
        if (r.nextDouble() < 0.2 && cooked != 2) {          //Le plat est froid donc il retourne chauffer
            System.out.println("Picard froid: retourne chauffer");
            Scheduler.getInstance().addEvent(new ServePicard(customer, microwave, 2, getStartingTime() + 150));
        } else {                                            //On peut servir le plat
            microwave.setFree(true);
            ControllerDevices.getInstance().getFreeDevices().replace("Microwave", ControllerDevices.getInstance().getFreeDevices().get("Microwave") + 1);
            Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime() + 1));
        }
    }

}
