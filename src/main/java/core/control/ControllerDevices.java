package core.control;

import core.Device;

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
        int id = 0;

        //Oven
        oven = new ArrayList<Device>(NB_OVEN);
        for(int i = 0; i < NB_OVEN; i++){
            oven.add(new Device(i, "Oven"));
        }
        id = NB_OVEN;

        //Microwave
        microwave = new ArrayList<Device>(NB_MICROWAVE);
        for(int i = id ; i < NB_MICROWAVE + id; i++){
            microwave.add(new Device(i, "Microwave"));
        }
        id += NB_MICROWAVE;

        //Cafetiere
        cafetiere = new ArrayList<Device>(NB_CAFETIERE);
        for(int i = id ; i < NB_CAFETIERE + id; i++){
            cafetiere.add(new Device(i, "Cafetiere"));
        }
        id += NB_CAFETIERE;

        //Kettle
        kettle = new ArrayList<Device>(NB_KETTLE);
        for(int i = id ; i < NB_KETTLE + id; i++){
            kettle.add(new Device(i, "Kettle"));
        }
        id += NB_KETTLE;

        //Cocoa
        cocoa = new ArrayList<Device>(NB_COCOA);
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

}

