package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerDevices {
    private ArrayList<Device> oven;
    private ArrayList<Device> microwave;
    private ArrayList<Device> cafetiere;
    private ArrayList<Device> kettle;
    private ArrayList<Device> cocoa;
    private HashMap<String, Integer> freeDevices;

    public ControllerDevices(){
        freeDevices = new HashMap<>();
        freeDevices.put("Oven",0);
        freeDevices.put("Microwave",0);
        freeDevices.put("Cafetiere",0);
        freeDevices.put("Kettle",0);
        freeDevices.put("Cocoa",0);
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

    public void setFreeDevices(HashMap<String, Integer> freeDevices) {
        this.freeDevices = freeDevices;
    }
}
