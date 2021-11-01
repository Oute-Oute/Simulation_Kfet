package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerDevices {
    ArrayList<Device> Oven;
    ArrayList<Device> Microwave;
    ArrayList<Device> Cafetiere;
    ArrayList<Device> Kettle;
    ArrayList<Device> 
    HashMap<String, Integer> freeDevices;

    public ArrayList<Device> getOven() {
        return Oven;
    }

    public void setOven(ArrayList<Device> oven) {
        Oven = oven;
    }

    public ArrayList<Device> getMicrowave() {
        return Microwave;
    }

    public void setMicrowave(ArrayList<Device> microwave) {
        Microwave = microwave;
    }
}
