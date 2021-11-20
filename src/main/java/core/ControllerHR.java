package core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerHR extends WaitingList {

    private int nbCashier, nbCooks, nbkfetiers;

    private ArrayList<Kfetier> cashier;
    private ArrayList<Kfetier> cooks;
    private ArrayList<Kfetier> kfetiers;
    private HashMap<String, Integer> freeKfetier;

    public ControllerHR(){
        int id = 20; //TODO: vérifier si y a pas un pb

        //Cashier
        cashier = new ArrayList<>(nbCashier);
        for(int i = id; i < nbCashier; i++){
            cashier.add(new Kfetier(i, "Cashier"));
        }
        id += nbCashier;

        //Cooks
        cooks = new ArrayList<>(nbCooks);
        for(int i = id ; i < nbCooks + id; i++){
            cooks.add(new Kfetier(i, "Cook"));
        }
        id += nbCooks;

        //Kfetier
        kfetiers = new ArrayList<>(nbkfetiers);
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
     * @param customer client
     */
    public void newCustomer(Customer customer) throws ClassNotFoundException, NoSuchMethodException {
        int time = 60; //le temps que met cet event à se réaliser
        int i = 0;
        boolean found = false;

        //S'il y a un caissier de libre
        if(freeKfetier.get("Cashier") > 0) {

            //On cherche quel caissier est libre
            while (i < cashier.size() && !found) {
                if (cashier.get(i).getFree()) {
                    //On passe le caissier à occupé
                    cashier.get(i).setFree(false);
                    found = true;
                } else {
                    i++;
                }
            }
            freeKfetier.replace("Cashier", freeKfetier.get("Cashier") - 1);

            //On set le temps à attendre
            time += customer.getPaymentDuration();


            //on définit les différents éléments de la méthode à appeler pour créer l'event
            //Nom de la méthode et type de ses arguments
            String name = "endPayment";
            String param1 = "Customer";
            String param2 = "core.Kfetier";

            //mettre les types des arguments dans un array de type Class
            Class[] args = new Class[2];
            args[1] = Class.forName(param1);
            args[2] = Class.forName(param2);

            //sauvegarder les paramètres à donner à la méthode quand on l'appelera
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(customer);
            parameters.add(cashier.get(i));

            //Créer la méthode et créer l'event pour l'ajouter à la liste d'event à lancer
            Method method = getClass().getMethod(name, args);
            new Event(Event.getCurrentTime()+time, method, parameters).addEvent();

        }
        else {
            //Le client est ajouté à la liste d'attente pré order
            getPreOrder().add(customer);
        }
    }

    /**
     * Event fin du paiement
     * @param customer client en train de payer
     * @param cashier caissier encaissant le client
     */
    public void endPayment(Customer customer, Kfetier cashier ) throws ClassNotFoundException, NoSuchMethodException {

        //Libère le caissier
        cashier.setFree(true);
        freeKfetier.replace("Cashier", freeKfetier.get("Cashier") + 1);

        //Lance la préparation de commande
        preparationOrder(customer);

        //Retire le client de la pré order
        getPreOrder().remove(customer);

        //Si la liste Pré Order n'est pas vide, on appelle le client suivant
        if( !getPreOrder().isEmpty() ){
            newCustomer(getPreOrder().get(0));
        }

    }

    public void preparationOrder(Customer customer){
        //On ajoute le client à la liste d'attente post order
        getPostOrder().add(customer);

        //on cherche si quelque chose est dispo pour lancer sa commande, sinon l'algo le trouvera plus tard
        searchGlobal(customer);
        searchPizza(customer);
    }
}
