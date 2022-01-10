package classes;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID =1L;
    private static int cptPayement = 3, fastLydiaPayement = 7, cashPayement = 15, slowLydiaPayement = 20;
    private static ArrayList<Integer> payement = new ArrayList<>(Stream.of(cptPayement, fastLydiaPayement, cashPayement,slowLydiaPayement).collect(Collectors.toList()));

    private Boolean kfetier;
    private Order order;
    public int id;
    private int paymentDuration; //Cpt = 3s, Lydia rapide = 7s, Liquide = 15s, Lydia lent = 20s
    private int arrivalTick; //tick de l'heure d'arrivée
    private int arrivalTime;
    private int tpm = 60;//ticks par minutes pour la génération temporelle

    private int departureTime;

    public Customer(double probaKfetier,int fast,int cash, int slow, int cpt, int ArrivalTime,int max_order){
        fastLydiaPayement=fast;
        cashPayement=cash;
        slowLydiaPayement=slow;
        cptPayement=cpt;
        Random r = new Random();

        kfetier = !(r.nextDouble() > probaKfetier);

        if (kfetier){
            paymentDuration = cptPayement;
        } else {
            paymentDuration = payement.get(r.nextInt(payement.size()));
        }
        order = new Order(max_order);

        arrivalTick = Math.abs((int) (2000*r.nextGaussian()));
        arrivalTime = arrivalTick/tpm;

    }

    public Boolean getKfetier() {
        return kfetier;
    }

    public Order getOrder() {
        return order;
    }

    public int getPaymentDuration() {
        return paymentDuration;
    }

    public void setDepartureTime(int time){
        this.departureTime = time;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }
}