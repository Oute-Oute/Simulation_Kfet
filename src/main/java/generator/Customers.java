package generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Customers implements Serializable {
    private ArrayList<Customer> customers;
    private float customerFrequency;

    public Customers(int nbMaxCustomer, double proba,int fast,int cash, int slow, int cpt, int customerFrequency) {
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

    public ArrayList<Integer> arrivalTimeGenerator (int nbMaxCustomer, int customerFrequency){
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
        //Customers clientele = new Customers(size, customerFrequency);
    //}
}
