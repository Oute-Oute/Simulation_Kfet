package generator;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.*;

public class Customer {

    private static final double PROBA_KFETIER = 0.06;
    private static final int CPT_PAYMENT = 3, FAST_LYDIA_PAYMENT = 7, CASH_PAYMENT = 15, SLOW_LYDIA_PAYMENT = 20;
    private static final ArrayList<Integer> PAYMENT = new ArrayList<Integer>(Stream.of(CPT_PAYMENT, FAST_LYDIA_PAYMENT, CASH_PAYMENT,SLOW_LYDIA_PAYMENT).collect(Collectors.toList()));

    private Boolean kfetier;
    private Order order;
    private int paymentDuration; //Cpt = 3s, Lydia rapide = 7s, Liquide = 15s, Lydia lent = 20s
    private int arrivalTime;

    public Customer(int arrivalTime){
        Random r = new Random();

        if(r.nextDouble() > PROBA_KFETIER){
            kfetier = false;
        } else kfetier = true;

        if (kfetier){
            paymentDuration = CPT_PAYMENT;
        } else {
            paymentDuration = PAYMENT.get(r.nextInt(PAYMENT.size()));
        }
        order = new Order();

        //TODO: Initialiser arrivalTime
    }

    public Boolean getKfetier() {
        return kfetier;
    }

    public Order getOrder() {
        return order;
    }
}
