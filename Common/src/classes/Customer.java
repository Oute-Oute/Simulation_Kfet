package classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Customer.
 */
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean kfetier;
    private Order order;
    private int paymentDuration;
    private ArrayList<Integer> payment;
    private int arrivalTick; //tick de l'heure d'arrivée
    private int arrivalTime;
    private int cptPayment;
    private int fastLydiaPayment;
    private int cashPayment;
    private int slowLydiaPayment;
    private int tpm = 60;//ticks par minutes pour la génération temporelle

    private int departureTime;

    /**
     * Instantiates a new Customer.
     *
     * @param probaKfetier the probability of being a kfetier
     * @param fast         the fastLydia payment time
     * @param cash         the cash payment time
     * @param slow         the slowLydia payment time
     * @param cpt          the account payment time
     * @param max_order    the max number of each article that can be ordered
     */
    public Customer(double probaKfetier, int fast, int cash, int slow, int cpt, int max_order) {
        fastLydiaPayment = fast;
        cashPayment = cash;
        slowLydiaPayment = slow;
        cptPayment = cpt;
        payment = new ArrayList<>(Stream.of(cptPayment, fastLydiaPayment, cashPayment, slowLydiaPayment).collect(Collectors.toList()));
        Random r = new Random();

        kfetier = !(r.nextDouble() > probaKfetier);

        if (kfetier) {
            paymentDuration = cptPayment;
        } else {
            paymentDuration = payment.get(r.nextInt(payment.size()));
        }
        order = new Order(max_order);

        arrivalTick = Math.abs((int) (2000 * r.nextGaussian()));
        arrivalTime = arrivalTick / tpm;
    }

    public Boolean getKfetier() {
        return kfetier;
    }

    /**
     * Gets the order.
     *
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Gets the payment duration.
     *
     * @return the payment duration
     */
    public int getPaymentDuration() {
        return paymentDuration;
    }

    /**
     * Sets departure time.
     *
     * @param time the time
     */
    public void setDepartureTime(int time) {
        this.departureTime = time;
    }

    /**
     * Gets arrival time.
     *
     * @return the arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets departure time.
     *
     * @return the departure time
     */
    public int getDepartureTime() {
        return departureTime;
    }
}