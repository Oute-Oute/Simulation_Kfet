package main.java.control;

import main.java.Device;

import java.util.ArrayList;
import java.util.HashMap;

public final class ControllerDevices {

    private static int NB_OVEN = 8, NB_MICROWAVE = 3, NB_CAFETIERE = 1, NB_KETTLE = 1, NB_COCOA = 1;
    private static ControllerDevices controllerDevicesInstance = new ControllerDevices();

    private ArrayList<Device> oven;
    private ArrayList<Device> microwave;
    private ArrayList<Device> cafetiere;
    private ArrayList<Device> kettle;
    private ArrayList<Device> cocoa;
    private HashMap<String, Integer> freeDevices;

    private ControllerDevices(){
        int id;

        //Oven
        oven = new ArrayList<>(NB_OVEN);
        for(int i = 0; i < NB_OVEN; i++){
            oven.add(new Device(i, "Oven"));
        }
        id = NB_OVEN;

        //Microwave
        microwave = new ArrayList<>(NB_MICROWAVE);
        for(int i = id ; i < NB_MICROWAVE + id; i++){
            microwave.add(new Device(i, "Microwave"));
        }
        id += NB_MICROWAVE;

        //Cafetiere
        cafetiere = new ArrayList<>(NB_CAFETIERE);
        for(int i = id ; i < NB_CAFETIERE + id; i++){
            cafetiere.add(new Device(i, "Cafetiere"));
        }
        id += NB_CAFETIERE;

        //Kettle
        kettle = new ArrayList<>(NB_KETTLE);
        for(int i = id ; i < NB_KETTLE + id; i++){
            kettle.add(new Device(i, "Kettle"));
        }
        id += NB_KETTLE;

        //Cocoa
        cocoa = new ArrayList<>(NB_COCOA);
        for(int i = id ; i < NB_COCOA + id; i++){
            cocoa.add(new Device(i, "Cocoa"));
        }

        // FreeDevices
        freeDevices = new HashMap<>();
        freeDevices.put("Oven",NB_OVEN);
        freeDevices.put("Microwave",NB_MICROWAVE);
        freeDevices.put("Cafetiere",NB_CAFETIERE);
        freeDevices.put("Kettle",NB_KETTLE);
        freeDevices.put("Cocoa",NB_COCOA);
    }

    public static ControllerDevices getInstance(){
        if (controllerDevicesInstance == null){
            controllerDevicesInstance = new ControllerDevices();
        }
        return controllerDevicesInstance;
    }

    public ArrayList<Device> getOven() {
        return oven;
    }

    public void setOven(ArrayList<Device> oven) {
        this.oven = oven;
    }

    public ArrayList<Device> getMicrowave() {
        return microwave;
    }

    public void setMicrowave(ArrayList<Device> microwave) {
        this.microwave = microwave;
    }

    public ArrayList<Device> getCafetiere() {
        return cafetiere;
    }

    public void setCafetiere(ArrayList<Device> cafetiere) {
        this.cafetiere = cafetiere;
    }

    public ArrayList<Device> getKettle() {
        return kettle;
    }

    public void setKettle(ArrayList<Device> kettle) {
        this.kettle = kettle;
    }

    public ArrayList<Device> getCocoa() {
        return cocoa;
    }

    public void setCocoa(ArrayList<Device> cocoa) {
        this.cocoa = cocoa;
    }

    public HashMap<String, Integer> getFreeDevices() {
        return freeDevices;
    }

    /**
     * Cherche quel four est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait un four de libre
     * TODO: ptetre une exception du coup
     * @return la position du four libre dans son arraylist
     */
    public int whichOven(){
        int i = 0;
        boolean found = false;
        //On cherche quel four est libre
        while (i < oven.size() && !found) {
            if (oven.get(i).getFree()) {
                //On passe le four à occupé et on change le nb de fours libres
                oven.get(i).setFree(false);
                freeDevices.replace("Oven", freeDevices.get("Oven") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Cherche quel microonde est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait un microonde de libre
     * TODO: ptetre une exception du coup
     * @return la position du microonde libre dans son arraylist
     */
    public int whichMicrowave(){
        int i = 0;
        boolean found = false;
        //On cherche quel microonde est libre
        while (i < microwave.size() && !found) {
            if (microwave.get(i).getFree()) {
                //On passe le microonde à occupé et on change le nb de microonde libres
                microwave.get(i).setFree(false);
                freeDevices.replace("Microwave", freeDevices.get("Microwave") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Cherche quelle cafetiere est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait une cafetiere de libre
     * TODO: ptetre une exception du coup
     * @return la position de la cafetiere libre dans son arraylist
     */
    public int whichCafetiere(){
        int i = 0;
        boolean found = false;
        //On cherche quelle cafetiere est libre
        while (i < cafetiere.size() && !found) {
            if (cafetiere.get(i).getFree()) {
                //On passe la cafetiere à occupée et on change le nb de cafetiere libres
                cafetiere.get(i).setFree(false);
                freeDevices.replace("Cafetiere", freeDevices.get("Cafetiere") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Cherche quelle machine à choco est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait une machine à choco de libre
     * TODO: ptetre une exception du coup
     * @return la position de la machine à choco libre dans son arraylist
     */
    public int whichCocoa(){
        int i = 0;
        boolean found = false;
        //On cherche quelle machine à choco est libre
        while (i < cocoa.size() && !found) {
            if (cocoa.get(i).getFree()) {
                //On passe la machine à choco à occupée et on change le nb de machine à choco libres
                cocoa.get(i).setFree(false);
                freeDevices.replace("Cocoa", freeDevices.get("Cocoa") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Cherche quelle bouilloire est actuellement libre
     * Cette méthode ne doit être lancée qu'après s'être assuré qu'il y avait une bouilloire de libre
     * TODO: ptetre une exception du coup
     * @return la position de la bouilloire libre dans son arraylist
     */
    public int whichKettle(){
        int i = 0;
        boolean found = false;
        //On cherche quelle bouilloire est libre
        while (i < kettle.size() && !found) {
            if (kettle.get(i).getFree()) {
                //On passe la bouilloire à occupée et on change le nb de bouilloire libres
                kettle.get(i).setFree(false);
                freeDevices.replace("Kettle", freeDevices.get("Kettle") - 1);
                found = true;
            } else {
                i++;
            }
        }
        return i;
    }
}

