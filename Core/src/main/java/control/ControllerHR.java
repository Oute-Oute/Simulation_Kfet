package main.java.control;

import kfet.CoreController;
import main.java.Kfetier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Controller hr.
 */
public final class ControllerHR{

    private int nbCashier, nbCooks, nbKfetiers;
    private static ControllerHR controllerHRInstance;

    private ArrayList<Kfetier> cashier;
    private ArrayList<Kfetier> cooks;
    private ArrayList<Kfetier> kfetiers;
    private final HashMap<String, Integer> freeKfetier;

    private ControllerHR(int nbCooks, int nbCashier, int nbKfetiers){
        this.nbKfetiers = nbKfetiers;
        this.nbCooks = nbCooks;
        this.nbCashier = nbCashier;
        int id = 20;

        //Cashier
        cashier = new ArrayList<>(nbCashier);
        for(int i = id; i < id+2; i++){
            cashier.add(new Kfetier(i, "Cashier"));
        }
        id += nbCashier;

        //Cooks
        cooks = new ArrayList<>(nbCooks);
        for(int i = id ; i < id+2; i++){
            cooks.add(new Kfetier(i, "Cook"));
        }
        id += nbCooks;

        //Kfetier
        kfetiers = new ArrayList<>(nbKfetiers);
        for(int i = id; i < id+2; i++){
            kfetiers.add(new Kfetier(i, "Kfetier"));
        }

        //FreeKfetier
        freeKfetier = new HashMap<>();
        freeKfetier.put("Cashier", nbCashier);
        freeKfetier.put("Cook", nbCooks);
        freeKfetier.put("Kfetier", nbKfetiers);
    }

    /**
     * Get instance controller hr.
     *
     * @return the controller hr
     */
    public static ControllerHR getInstance(){
        return controllerHRInstance;
    }

    /**
     * Set instance.
     *
     * @param nbCooks    the nb cooks
     * @param nbCashier  the nb cashier
     * @param nbKfetiers the nb kfetiers
     */
    public static void setInstance(int nbCooks, int nbCashier, int nbKfetiers){
        controllerHRInstance = new ControllerHR(nbCooks,nbCashier,nbKfetiers);
    }

    /**
     * Gets cashier.
     *
     * @return the cashier
     */
    public ArrayList<Kfetier> getCashier() {
        return cashier;
    }

    /**
     * Gets cooks.
     *
     * @return the cooks
     */
    public ArrayList<Kfetier> getCooks() {
        return cooks;
    }

    /**
     * Gets kfetiers.
     *
     * @return the kfetiers
     */
    public ArrayList<Kfetier> getKfetiers() {
        return kfetiers;
    }

    /**
     * Gets free kfetier.
     *
     * @return the free kfetier hashmap
     */
    public HashMap<String, Integer> getFreeKfetier() {
        return freeKfetier;
    }

    /**
     * Search which Cashier is currently free
     * Must only be called when we are sure a cashier is free
     *
     * @return the cashier's position in its arraylist
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
                CoreController.getInstance().notFree(i+20);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Search which kfetier is currently free
     * Must only be called when we are sure a kfetier is free
     *
     * @param device the device
     * @return the kfetier's position in its arraylist
     */
    public int whichKfetier(int device,int status){
        int i = 0;
        boolean found = false;
        //On cherche quel Kfetier est libre
        while (i < kfetiers.size() && !found) {
            if (kfetiers.get(i).getFree()) {
                //On passe le kfetier à occupé et on change le nb de kfetiers libres
                kfetiers.get(i).setFree(false);
                ControllerHR.getInstance().freeKfetier.replace("Kfetier", freeKfetier.get("Kfetier") - 1);
                if(status==0) {
                    CoreController.getInstance().transition(CoreController.getInstance().humans.get(i + 4), device);
                }
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Search which Cook is currently free
     * Must only be called when we are sure a cook is free
     *
     * @param device the device
     * @return the cook's position in its arraylist
     */
    public int whichCook(int device,int status){
        int i = 0;
        boolean found = false;
        //On cherche quel cuisinier est libre
        while (i < cooks.size() && !found) {
            if (cooks.get(i).getFree()) {
                //On passe le cuisinier à occupé et on change le nb de cuisiniers libres
                cooks.get(i).setFree(false);
                ControllerHR.getInstance().freeKfetier.replace("Cook", freeKfetier.get("Cook") - 1);
                CoreController.getInstance().transition(CoreController.getInstance().humans.get(i+2),device);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Gets nb cashier.
     *
     * @return the nb cashier
     */
    public int getNbCashier() {
        return nbCashier;
    }

    /**
     * Gets nb cooks.
     *
     * @return the nb cooks
     */
    public int getNbCooks() {
        return nbCooks;
    }

    /**
     * Gets nb kfetiers.
     *
     * @return the nb kfetiers
     */
    public int getNbKfetiers() {
        return nbKfetiers;
    }
}
