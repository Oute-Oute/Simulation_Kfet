package main.java.control;

import main.java.Device;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Controller devices.
 */
public final class ControllerDevices {

    private static final int NB_OVEN = 8;
    private static final int NB_MICROWAVE = 3;
    private static final int NB_CAFETIERE = 2;
    private static final int NB_KETTLE = 2;
    private static final int NB_COCOA = 1;
    private static ControllerDevices controllerDevicesInstance = new ControllerDevices();

    private ArrayList<Device> oven;
    private ArrayList<Device> microwave;
    private ArrayList<Device> cafetiere;
    private ArrayList<Device> kettle;
    private ArrayList<Device> cocoa;
    private final HashMap<String, Integer> freeDevices;

    private ControllerDevices() {
        int id = 0;

        //Oven
        oven = new ArrayList<>(NB_OVEN);
        for (int i = 0; i < NB_OVEN; i++) {
            oven.add(new Device(i, "Oven"));
        }
        id = NB_OVEN;

        //Microwave
        microwave = new ArrayList<>(NB_MICROWAVE);
        for (int i = id; i < NB_MICROWAVE + id; i++) {
            microwave.add(new Device(i, "Microwave"));
        }
        id += NB_MICROWAVE;

        //Cafetiere
        cafetiere = new ArrayList<>(NB_CAFETIERE);
        for (int i = id; i < NB_CAFETIERE + id; i++) {
            cafetiere.add(new Device(i, "Cafetiere"));
        }
        id += NB_CAFETIERE;

        //Kettle
        kettle = new ArrayList<>(NB_KETTLE);
        for (int i = id; i < NB_KETTLE + id; i++) {
            kettle.add(new Device(i, "Kettle"));
        }
        id += NB_KETTLE;

        //Cocoa
        cocoa = new ArrayList<>(NB_COCOA);
        for (int i = id; i < NB_COCOA + id; i++) {
            cocoa.add(new Device(i, "Cocoa"));
        }

        // FreeDevices
        freeDevices = new HashMap<>();
        freeDevices.put("Oven", NB_OVEN);
        freeDevices.put("Microwave", NB_MICROWAVE);
        freeDevices.put("Cafetiere", NB_CAFETIERE);
        freeDevices.put("Kettle", NB_KETTLE);
        freeDevices.put("Cocoa", NB_COCOA);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ControllerDevices getInstance() {
        if (controllerDevicesInstance == null) {
            controllerDevicesInstance = new ControllerDevices();
        }
        return controllerDevicesInstance;
    }

    /**
     * Gets oven.
     *
     * @return the oven
     */
    public ArrayList<Device> getOven() {
        return oven;
    }

    /**
     * Gets microwave.
     *
     * @return the microwave
     */
    public ArrayList<Device> getMicrowave() {
        return microwave;
    }

    /**
     * Gets cafetiere.
     *
     * @return the cafetiere
     */
    public ArrayList<Device> getCafetiere() {
        return cafetiere;
    }

    /**
     * Gets kettle.
     *
     * @return the kettle
     */
    public ArrayList<Device> getKettle() {
        return kettle;
    }

    /**
     * Gets cocoa.
     *
     * @return the cocoa
     */
    public ArrayList<Device> getCocoa() {
        return cocoa;
    }

    /**
     * Gets free devices.
     *
     * @return the free devices hashmap
     */
    public HashMap<String, Integer> getFreeDevices() {
        return freeDevices;
    }

    /**
     * Search which oven is currently free
     * Must only be called when we are sure an oven is free
     *
     * @return the oven's position in its arraylist
     */
    public int whichOven() {
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
     * Search which microwave is currently free
     * Must only be called when we are sure a microwave is free
     *
     * @return the microwave's position in its arraylist
     */
    public int whichMicrowave() {
        int i = 0;
        boolean found = false;
        //On cherche quel microonde est libre
        while (i < microwave.size() && !found) {
            if (microwave.get(i).getFree()) {
                //On passe le micro-onde à occupé et on change le nb de micro-onde libres
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
     * Search which cafetiere is currently free
     * Must only be called when we are sure a cafetiere is free
     *
     * @return the cafetiere's position in its arraylist
     */
    public int whichCafetiere() {
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
     * Search which Cocoa machine is currently free
     * Must only be called when we are sure a cocoa machine is free
     *
     * @return the cocoa machine's position in its arraylist
     */
    public int whichCocoa() {
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
     * Search which Kettle is currently free
     * Must only be called when we are sure a kettle is free
     *
     * @return the kettle's position in its arraylist
     */
    public int whichKettle() {
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

