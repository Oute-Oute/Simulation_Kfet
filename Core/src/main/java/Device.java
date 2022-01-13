package main.java;

import kfet.CoreController;
import main.java.control.Scheduler;

/**
 * The type Device.
 */
public class Device {

    private final int id;
    private String type;
    private Boolean isFree;
    private int occupationTime, startOccupated, endOccupated;
    private int nbUsed;

    /**
     * Instantiates a new Device.
     *
     * @param id   the id
     * @param type the type
     */
    public Device(int id, String type) {
        this.id = id;
        this.type = type;
        isFree = true;
        nbUsed = 0;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets free.
     *
     * @return the boolean.
     */
    public Boolean getFree() {
        return isFree;
    }

    /**
     * Sets free the device.
     *
     * @param free the boolean to set.
     */
    public void setFree(Boolean free) {
        isFree = free;
        if (free) {
            endOccupated = Scheduler.getInstance().getCurrentTime();
            occupationTime += (endOccupated - startOccupated);
            CoreController.getInstance().free(this.id);
        } else {
            startOccupated = Scheduler.getInstance().getCurrentTime();
            nbUsed++;
            CoreController.getInstance().notFree(this.id);
        }
    }

    /**
     * Gets the number of use of the device.
     *
     * @return the number of use
     */
    public int getNbUsed() {
        return nbUsed;
    }

    /**
     * Gets the occupation time.
     *
     * @return the occupation time
     */
    public int getOccupationTime() {
        return occupationTime;
    }
}
