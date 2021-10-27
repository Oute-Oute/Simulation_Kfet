package core;

import java.util.ArrayList;

public class ControllerDevices {
    ArrayList<Device> Oven;
    ArrayList<Device> Microwave;

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
