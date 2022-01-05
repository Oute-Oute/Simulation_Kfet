package main.java.control;

import main.java.Kfetier;

import java.util.ArrayList;
import java.util.HashMap;

public final class ControllerHR{

    private int nbCashier, nbCooks, nbKfetiers;
    private static ControllerHR controllerHRInstance;

    private ArrayList<Kfetier> cashier;
    private ArrayList<Kfetier> cooks;
    private ArrayList<Kfetier> kfetiers;
    private HashMap<String, Integer> freeKfetier;

    private ControllerHR(int nbCooks, int nbCashier, int nbKfetiers){
        this.nbKfetiers = nbKfetiers;
        this.nbCooks = nbCooks;
        this.nbCashier = nbCashier;
        int id = 20;

        //Cashier
        cashier = new ArrayList<>(nbCashier);
        for(int i = id; i < id+nbCashier; i++){
            cashier.add(new Kfetier(i, "Cashier"));
        }
        id += nbCashier;

        //Cooks
        cooks = new ArrayList<>(nbCooks);
        for(int i = id ; i < id+nbCooks; i++){
            cooks.add(new Kfetier(i, "Cook"));
        }
        id += nbCooks;

        //Kfetier
        kfetiers = new ArrayList<>(nbKfetiers);
        for(int i = id; i < id+nbKfetiers; i++){
            kfetiers.add(new Kfetier(i, "Kfetier"));
        }

        //FreeKfetier
        freeKfetier = new HashMap<>();
        freeKfetier.put("Cashier", nbCashier);
        freeKfetier.put("Cook", nbCooks);
        freeKfetier.put("Kfetier", nbKfetiers);
    }

    public static ControllerHR getInstance(){
        /*if (controllerHRInstance == null){
            controllerHRInstance = new ControllerHR();
        }*/
        //TODO: vérifier pb ici si jamais
        return controllerHRInstance;
    }
    public static void setInstance(int nbCooks, int nbCashier, int nbKfetiers){
        controllerHRInstance = new ControllerHR(nbCooks,nbCashier,nbKfetiers);
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
     * Cherche quel caissier est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait un caissier de libre
     * @return la position du caissier libre dans son arraylist
     */
    public int whichCashier(){
        int i = 0;
        boolean found = false;
        //On cherche quel caissier est libre
        while (i < cashier.size() && !found) {
            if (cashier.get(i).getFree()) {
                //On passe le caissier à occupé et on change le nb de caissiers libres
                cashier.get(i).setFree(false);
                ControllerHR.getInstance().freeKfetier.replace("Cashier", freeKfetier.get("Cashier") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Cherche quel Kfetier est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait un Kfetier de libre
     * @return la position du Kfetier libre dans son arraylist
     */
    public int whichKfetier(){
        int i = 0;
        boolean found = false;
        //On cherche quel Kfetier est libre
        while (i < kfetiers.size() && !found) {
            if (kfetiers.get(i).getFree()) {
                //On passe le kfetier à occupé et on change le nb de kfetiers libres
                kfetiers.get(i).setFree(false);
                ControllerHR.getInstance().freeKfetier.replace("Kfetier", freeKfetier.get("Kfetier") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Cherche quel cuisinier est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait un cuisinier de libre
     * @return la position du cuisinier libre dans son arraylist
     */
    public int whichCook(){
        int i = 0;
        boolean found = false;
        //On cherche quel cuisinier est libre
        while (i < cooks.size() && !found) {
            if (cooks.get(i).getFree()) {
                //On passe le cuisinier à occupé et on change le nb de cuisiniers libres
                cooks.get(i).setFree(false);
                ControllerHR.getInstance().freeKfetier.replace("Cook", freeKfetier.get("Cook") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    public void setNbCashier(int nbCashier) {
        this.nbCashier = nbCashier;
    }

    public void setNbCooks(int nbCooks) {
        this.nbCooks = nbCooks;
    }

    public void setNbKfetiers(int nbKfetiers) {
        this.nbKfetiers = nbKfetiers;
    }

    public int getNbCashier() {
        return nbCashier;
    }

    public int getNbCooks() {
        return nbCooks;
    }

    public int getNbKfetiers() {
        return nbKfetiers;
    }
}
