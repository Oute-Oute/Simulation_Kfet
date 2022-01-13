package main.java;

import kfet.CoreController;
import main.java.control.Scheduler;

/**
 * The type Kfetier.
 */
public class Kfetier {
    private final int id;
    private String type;
    private Boolean isFree;
    private int occupationTime, startOccupated, endOccupated;
    private int nbUse;

    /**
     * Instantiates a new Kfetier.
     *
     * @param id   the id
     * @param type the type of kfetier
     */
    public Kfetier(int id, String type) {
        this.id = id;
        this.type = type;
        isFree = true;
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
     * @return the type of kfetier
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type of kfetier
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets free.
     *
     * @return true if free, false if not
     */
    public Boolean getFree() {
        return isFree;
    }

    /**
     * Sets free.
     *
     * @param free the free
     */
    public void setFree(Boolean free) {
        isFree = free;

        if (free) {
            endOccupated = Scheduler.getInstance().getCurrentTime();
            occupationTime += (endOccupated - startOccupated);
        } else {
            startOccupated = Scheduler.getInstance().getCurrentTime();
            nbUse++;
        }
    }

    /**
     * Gets the number of use of the device.
     *
     * @return the number of use
     */
    public int getNbUse() {
        return nbUse;
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
