package generator;

import java.util.ArrayList;

public class Customers {
    private static final int NB_CUSTOMER = 50;

    private ArrayList<Customer> customers;
    private float customerFrequency;

    public Customers() {
        customers = new ArrayList<Customer>();

        for(int i = 0; i< NB_CUSTOMER; i++){
            customers.add(new Customer());
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }
}
