package core;

import java.util.ArrayList;

public abstract class Event {
    private int startingTime;

    public Event(int startingTime){
        this.startingTime = startingTime;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public abstract void run();
}
