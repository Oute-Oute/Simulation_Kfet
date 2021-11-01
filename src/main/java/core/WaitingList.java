package core;

import java.util.ArrayList;

public class WaitingList {
    ArrayList<Customer> preOrder;
    ArrayList<Customer> postOrder;

    public WaitingList() {

    }

    public ArrayList<Customer> getPreOrder() {
        return preOrder;
    }

    public ArrayList<Customer> getPostOrder() {
        return postOrder;
    }

    public void searchGlobal(Customer customer){
        //TODO:
        //quelle sont les ressources de libre / 1) micro onde 2) ramen 3) café/chocolat
        //Si 1) libre, alors parcours la post a la recherche d'une commande de 1)
        //Si rien alors on regarde si 2) libre et et cherche dans post etc
        //Si commande, on appelle debut commande avec en paramètre le client

    }

    public void searchPizza(Customer customer){
        //TODO:
        //est-ce que un four est libre?
        //recherche d'une commande de pizza
        //appel debut pizza avec customer en paramètre


}
