package core;

import com.kfet.core.CoreController;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerHR extends WaitingList {

    private int nbCashier, nbCooks, nbkfetiers;

    private ArrayList<Kfetier> cashier;
    private ArrayList<Kfetier> cooks;
    private ArrayList<Kfetier> kfetiers;
    private HashMap<String, Integer> freeKfetier;

    public ControllerHR(){
        int id = 0;

        //Cashier
        cashier = new ArrayList<Kfetier>(nbCashier);
        for(int i = 0; i < nbCashier; i++){
            cashier.add(new Kfetier(i, "Cashier"));
        }
        id = nbCashier;

        //Cooks
        cooks = new ArrayList<Kfetier>(nbCooks);
        for(int i = id ; i < nbCooks + id; i++){
            cooks.add(new Kfetier(i, "Cook"));
        }
        id += nbCooks;

        //Kfetier
        kfetiers = new ArrayList<Kfetier>(nbkfetiers);
        for(int i = id ; i < nbkfetiers + id; i++){
            kfetiers.add(new Kfetier(i, "Kfetier"));
        }

        //FreeKfetier
        freeKfetier = new HashMap<>();
        freeKfetier.put("Cashier", nbCashier);
        freeKfetier.put("Cook", nbCooks);
        freeKfetier.put("Kfetier", nbkfetiers);
    }

    public ArrayList<Kfetier> getCashier() {
        return cashier;
    }

    public void setCashier(ArrayList<Kfetier> cashier) {
        this.cashier = cashier;
    }

    public ArrayList<Kfetier> getCooks() {
        return cooks;
    }

    public void setCooks(ArrayList<Kfetier> cooks) {
        this.cooks = cooks;
    }

    public ArrayList<Kfetier> getKfetiers() {
        return kfetiers;
    }

    public void setKfetiers(ArrayList<Kfetier> kfetiers) {
        this.kfetiers = kfetiers;
    }

    public HashMap<String, Integer> getFreeKfetier() {
        return freeKfetier;
    }

    /**
     * Event arrivée d'un client
     * @param customer
     */
    public void newCustomer(Customer customer){
        int time = 60;

        if(freeKfetier.get("Cashier") > 0){
            cashier.get(0).setFree(false);
            time += customer.getPaymentDuration();

            //Attendre time secondes puis appeler fin de paiement
        }
        else {
            getPreOrder().add(customer);
        }
    }
}
