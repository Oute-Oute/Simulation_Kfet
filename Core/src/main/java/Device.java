package main.java;

import kfet.CoreController;
import main.java.control.Scheduler;

public class Device{

    private int id;
    private String type;
    private Boolean isFree;
    private int occupationTime, startOccupated, endOccupated;
    private int nbUsed;

    public Device(int id, String type) {
        this.id = id;
        this.type = type;
        isFree = true;
        nbUsed = 0;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
        if(free){
            endOccupated = Scheduler.getInstance().getCurrentTime();
            occupationTime += (endOccupated - startOccupated);
            CoreController.getInstance().free(this.id);
        } else {
            startOccupated = Scheduler.getInstance().getCurrentTime();
            nbUsed++;
            CoreController.getInstance().notFree(this.id);
        }
    }

    public int getNbUsed() {
        return nbUsed;
    }

    public int getOccupationTime() {
        return occupationTime;
    }
}
