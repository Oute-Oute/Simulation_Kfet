package main.java;

/**
 * The type Event.
 */
public abstract class Event {
    private final int startingTime;

    /**
     * Instantiates a new Event.
     *
     * @param startingTime the starting time
     */
    public Event(int startingTime) {
        this.startingTime = startingTime;
    }

    /**
     * Gets starting time.
     *
     * @return the starting time
     */
    public int getStartingTime() {
        return startingTime;
    }

    /**
     * The run method.
     * Will be defined in every event class to run each event.
     */
    public abstract void run();
}
