package generator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Customers {
    private ArrayList<Customer> customers;

    public Customers(int nbMaxCustomer, int customerFrequency) {
        customers = new ArrayList<>();

        ArrayList<Integer> arrivalTime = arrivalTimeGenerator(nbMaxCustomer, customerFrequency);

        for(int i = 0; i< nbMaxCustomer; i++){
            customers.add(new Customer( arrivalTime.get(i)));
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public ArrayList<Integer> arrivalTimeGenerator (int nbMaxCustomer, int customerFrequency){
        ArrayList<Integer> arrivalTimeCustomers = new ArrayList<Integer>();

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

    public static void generate(int size, int customerFrequency){
        Customers clientele = new Customers(size, customerFrequency);
    }
}
