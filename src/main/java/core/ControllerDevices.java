package core;

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



    //Coffee Methods
    public void coffeePrep(){
        //TODO everything
    }

    public void coffeeEnd(){
        //TODO everything
    }
    //Chocolate Methods
    public void chocolatePrep(){
        //TODO everything
    }

    public void chocolateEnd(){
        //TODO everything
    }

    //Ramen Methods
    public void boilingWaterStart(){
        //TODO everything
    }

    public void boilingWaterEnd(){
        //TODO everything
    }

    public void ramenEnd(){
        //TODO everything
    }

    //PicardMethods
    public void picardPrep(){
        //TODO everything
    }
    public void picardCooking(){
        //TODO everything
    }
    public void picardEnd(){
        //TODO everything
    }

    //PizzaMethods
    public void pizzaPrep(){
        //TODO everything
    }
    public void pizzaCooking(){
        //TODO everything
    }
    public void pizzaEnd(){
        //TODO everything
    }
}

