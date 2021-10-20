package generator;

import java.util.ArrayList;

public class Customers {
    private ArrayList<Customer> Customers;
    private float CustomerFrequency;

    public ArrayList<Customer> getCustomers() {
        return Customers;
    }

    public void addCustomer(Customer customer){
        Customers.add(customer);
    }
}
