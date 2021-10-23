package generator;

import java.io.Serializable;
import java.util.ArrayList;

public class Customers implements Serializable {
    private ArrayList<Customer> customers;
    private float customerFrequency;

    public Customers(int nbMaxCustomer, double proba,int fast,int cash, int slow, int cpt) {
        customers = new ArrayList<>();

        for(int i = 0; i< nbMaxCustomer; i++){
            customers.add(new Customer(proba,fast,cash,slow,cpt));
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    //public static void generate(int size){
       // Customers clientele = new Customers(size);
    //}
}
