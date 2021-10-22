package generator;

import java.util.ArrayList;

public class Customers {
    private ArrayList<Customer> customers;
    private float customerFrequency;

    public Customers(int nbMaxCustomer) {
        customers = new ArrayList<>();

        for(int i = 0; i< nbMaxCustomer; i++){
            customers.add(new Customer());
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void generate(){
        Customers clientele = new Customers(50);
    }
}
