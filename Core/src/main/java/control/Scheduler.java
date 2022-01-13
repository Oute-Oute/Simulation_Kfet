package main.java.control;

import java.io.PrintStream;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import kfet.CoreController;
import main.java.Event;
import main.java.WaitingList;


/**
 * The type Scheduler.
 */
public final class Scheduler {
    private static Scheduler SchedulerInstance = new Scheduler();
    private ArrayList<Event> incomingEvent = new ArrayList();
    private int currentTime = 0;
    private int status = 0;

    private Scheduler() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Scheduler getInstance() {
        if (SchedulerInstance == null) {
            SchedulerInstance = new Scheduler();
        }

        return SchedulerInstance;
    }

    /**
     * Gets current time.
     *
     * @return the current time
     */
    public int getCurrentTime() {
        return this.currentTime;
    }

    /**
     * Gets the number of event.
     *
     * @return the number of event
     */
    public int getNbEvent() {
        return this.incomingEvent.size();
    }

    /**
     * Add an event to the incomingEvent arraylist.
     *
     * @param event the event to add.
     */
    public void addEvent(Event event) {
        if (SchedulerInstance.incomingEvent.isEmpty()) {
            SchedulerInstance.incomingEvent.add(event);
        } else {
            boolean found = false;

            int i;
            for (i = 0; i < SchedulerInstance.incomingEvent.size() && !found; ++i) {
                if (event.getStartingTime() < SchedulerInstance.incomingEvent.get(i).getStartingTime()) {
                    SchedulerInstance.incomingEvent.add(i, event);
                    found = true;
                }
            }

            if (i == this.incomingEvent.size() && !found) {
                this.incomingEvent.add(i, event);
            }
        }

    }

    /**
     * Start the event when their starting time is the same as the current time.
     *
     * @param currentTime the current time
     * @throws InterruptedException the interrupted exception
     */
    public void startingEvent(int currentTime) throws InterruptedException {
        int i = 0;

        while (i < SchedulerInstance.getNbEvent() && SchedulerInstance.incomingEvent.get(i).getStartingTime() <= currentTime) {
            SchedulerInstance.incomingEvent.get(i).run();
            SchedulerInstance.incomingEvent.remove(i);
        }
    }

    /**
     * Pass the time in the simulation
     *
     * @throws InterruptedException the interrupted exception
     */
    public void passingTime() throws InterruptedException {

        Duration tick_duration = Duration.ofMillis(1200L);
        Clock baseClock = Clock.systemUTC();
        Clock newClock = Clock.systemUTC();
        LocalTime basetime = LocalTime.now();
        LocalTime newTime = LocalTime.now();
        PrintStream var10000 = System.out;
        HashMap var10001 = ControllerDevices.getInstance().getFreeDevices();

        while (this.currentTime <= 7200) {
            if (status == 0) {
                CoreController.getInstance().Reclock(currentTime);
                basetime = LocalTime.now();
                if (basetime.isAfter(newTime)) {
                    this.startingEvent(this.currentTime);
                    ++this.currentTime;
                    newTime = basetime.plusNanos(10000000L);
                }
            }
        }

        if (WaitingList.getInstance().getPostOrder().size() >= 1
                || WaitingList.getInstance().getPreOrder().size() >= 1) {
            CoreController.getInstance().End(1);

        } else {
            CoreController.getInstance().End(0);
        }
        this.currentTime = 7201;
    }

    /**
     * Start the simulation
     *
     * @throws InterruptedException the interrupted exception
     */
    public static void start() throws InterruptedException {
        getInstance().passingTime();
    }

    /**
     * Sets current time.
     *
     * @param currentTime the current time
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Set status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
