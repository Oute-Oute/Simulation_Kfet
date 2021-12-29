package classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Customers implements Serializable {
    @Serial
    private static final long serialVersionUID =1L;
    private ArrayList<Customer> customers;
    private float customerFrequency;

    public Customers(){
        customers = new ArrayList<>();
    }

    public Customers(int nbMaxCustomer, double proba,int fast,int cash, int slow, int cpt, double customerFrequency) {
        customers = new ArrayList<>();

        ArrayList<Integer> arrivalTime = arrivalTimeGenerator(nbMaxCustomer, customerFrequency);

        for(int i = 0; i< nbMaxCustomer; i++){
            customers.add(new Customer(proba,fast,cash,slow,cpt,arrivalTime.get(i)));
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public ArrayList<Integer> arrivalTimeGenerator (int nbMaxCustomer, double customerFrequency){
        ArrayList<Integer> arrivalTimeCustomers = new ArrayList<>();

       int globalTime = 0;
       int lastArrivalTime= 0;

       arrivalTimeCustomers.add(lastArrivalTime);
       Random r = new Random();

       while (globalTime < 7200){
           globalTime += 1;

           if (r.nextInt(1) <= ((globalTime - lastArrivalTime)*(globalTime - lastArrivalTime))/customerFrequency){
               arrivalTimeCustomers.add(globalTime);
               lastArrivalTime = globalTime;
           }
       }
       return arrivalTimeCustomers;
    }

    //public static void generate(int size, int customerFrequency){
        //java.Customers clientele = new java.Customers(size, customerFrequency);
    //}
}
