package main.java;

/**
 * The type Kfetier.
 */
public class Kfetier {
    private final int id;
    private String type;
    private Boolean isFree;

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
    }
}
