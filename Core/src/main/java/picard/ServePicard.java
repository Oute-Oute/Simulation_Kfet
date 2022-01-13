package main.java.picard;

import classes.Customer;

import main.java.Device;
import main.java.Event;
import main.java.WaitingList;
import main.java.control.ControllerDevices;
import main.java.control.ControllerHR;
import main.java.control.Scheduler;
import main.java.payment.ServeCustomer;

import java.util.Random;

public class ServePicard extends Event {

    private final Device microwave;
    private final Customer customer;
    private final int cooked;     //0 si pas cuit, 1 si cuit une fois, 2 si cuit 2 fois

    public ServePicard(Customer customer, Device microwave, int cooked, int startingTime) {
        super(startingTime);
        this.microwave = microwave;
        this.customer = customer;
        this.cooked = cooked;
    }

    public void run() {
        Random r = new Random();
        if (ControllerHR.getInstance().getFreeKfetier().get("Kfetier") > 0) {
            int position = ControllerHR.getInstance().whichKfetier(microwave.getId(),1);

            if (r.nextDouble() < 0.2 && cooked != 2) {          //Le plat est froid donc il retourne chauffer
                Scheduler.getInstance().addEvent(new ServePicard(customer, microwave, 2, getStartingTime() + 150));

            } else {                                            //On peut servir le plat
                microwave.setFree(true);
                ControllerDevices.getInstance().getFreeDevices().replace("Microwave", ControllerDevices.getInstance().getFreeDevices().get("Microwave") + 1);
                Scheduler.getInstance().addEvent(new ServeCustomer(customer, Scheduler.getInstance().getCurrentTime() + 1));
            }

            ControllerHR.getInstance().getKfetiers().get(position).setFree(true);
            ControllerHR.getInstance().getFreeKfetier().replace("Kfetier", ControllerHR.getInstance().getFreeKfetier().get("Kfetier") + 1);
        }
    }

}
