package core;

import java.util.ArrayList;

public class ControllerHR {
    private ArrayList<Kfetier> cashier;
    private ArrayList<Kfetier> cooks;
    private ArrayList<Kfetier> kfetiers;

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
}
