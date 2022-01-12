package classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Customers.
 */
public class Customers implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Customer> customers;
    private float customerFrequency;

    /**
     * Instantiates a new Customers.
     */
    public Customers() {
        customers = new ArrayList<>();
    }

    /**
     * Instantiates a new Customers.
     *
     * @param nbMaxCustomer     the max number of customer
     * @param proba             the proba of being a Kfetier
     * @param fast              the fastLydia payment time
     * @param cash              the cash payment time
     * @param slow              the slowLydia payment time
     * @param cpt               the account payment time
     * @param customerFrequency the customer frequency
     * @param max_order         the max order
     */
    public Customers(int nbMaxCustomer, double proba, int fast, int cash, int slow, int cpt, double customerFrequency, int max_order) {
        customers = new ArrayList<>();

        ArrayList<Integer> arrivalTime = arrivalTimeGenerator(nbMaxCustomer, customerFrequency);

        for (int i = 0; i < nbMaxCustomer; i++) {
            customers.add(new Customer(proba, fast, cash, slow, cpt, max_order));
        }
    }

    /**
     * Gets customers.
     *
     * @return the customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * Add customer.
     *
     * @param customer the customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Generates the arrivalTime of each customer
     *
     * @param nbMaxCustomer     the max number of customer
     * @param customerFrequency the customer frequency
     * @return the array list
     */
    public ArrayList<Integer> arrivalTimeGenerator(int nbMaxCustomer, double customerFrequency) {
        ArrayList<Integer> arrivalTimeCustomers = new ArrayList<>();

        int globalTime = 0;
        int lastArrivalTime = 0;

        arrivalTimeCustomers.add(lastArrivalTime);
        Random r = new Random();

        while (globalTime < 7200) {
            globalTime += 1;

            if (customerFrequency == 0) {
                arrivalTimeCustomers.add(7205);
            } else if (r.nextInt(1) <= ((globalTime - lastArrivalTime) * (globalTime - lastArrivalTime)) / customerFrequency) {
                arrivalTimeCustomers.add(globalTime);
                lastArrivalTime = globalTime;
            }
        }
        return arrivalTimeCustomers;
    }
}
