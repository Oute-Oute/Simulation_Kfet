package main.java.control;

import java.io.PrintStream;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kfet.CoreController;
import main.java.Event;
import main.java.WaitingList;
import main.java.report.ExportExcel;

public final class Scheduler {
    private static Scheduler SchedulerInstance = new Scheduler();
    private final ArrayList<Event> incomingEvent = new ArrayList();
    private int currentTime = 0;
    private int status=0;

    private Scheduler() {
    }

    public static Scheduler getInstance() {
        if (SchedulerInstance == null) {
            SchedulerInstance = new Scheduler();
        }

        return SchedulerInstance;
    }

    public int getCurrentTime() {
        return this.currentTime;
    }

    public int getnbEvent() {
        return this.incomingEvent.size();
    }

    public void addEvent(Event event) {
        if (SchedulerInstance.incomingEvent.isEmpty()) {
            SchedulerInstance.incomingEvent.add(event);
        } else {
            boolean found = false;

            int i;
            for(i = 0; i < SchedulerInstance.incomingEvent.size() && !found; ++i) {
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

    public void startingEvent(int currentTime) throws InterruptedException {
        byte i = 0;

        while(i < SchedulerInstance.getnbEvent() && SchedulerInstance.incomingEvent.get(i).getStartingTime() <= currentTime) {
            System.out.println(currentTime + " /7200");
            SchedulerInstance.incomingEvent.get(i).run();
            SchedulerInstance.incomingEvent.remove(i);

        }

    }

    public void passingTime() throws InterruptedException {

        Duration tick_duration = Duration.ofMillis(1200L);
        Clock baseClock = Clock.systemUTC();
        Clock newClock = Clock.systemUTC();
        LocalTime basetime = LocalTime.now();
        LocalTime newTime = LocalTime.now();
        PrintStream var10000 = System.out;
        HashMap var10001 = ControllerDevices.getInstance().getFreeDevices();
        var10000.println(var10001.get("Cafetiere") + " cafetieres de libre");


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
        System.out.println("Fin");
        if (WaitingList.getInstance().getPostOrder().size() >= 1
                || WaitingList.getInstance().getPreOrder().size() >= 1) {
            CoreController.getInstance().End(1);

        } else {
            CoreController.getInstance().End(0);
        }
        this.currentTime = 7201;
    }



    public static void start() throws InterruptedException {
        getInstance().passingTime();
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void setStatus(int status){
        this.status=status;
    }

}
