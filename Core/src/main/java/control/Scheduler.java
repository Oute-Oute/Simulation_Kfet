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
    private ArrayList<Event> incomingEvent = new ArrayList();
    private int currentTime = 0;

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
                if (event.getStartingTime() < (SchedulerInstance.incomingEvent.get(i)).getStartingTime()) {
                    SchedulerInstance.incomingEvent.add(i, event);
                    found = true;
                }
            }

            if (i == this.incomingEvent.size() && !found) {
                this.incomingEvent.add(i, event);
            }
        }

    }

    public void startingEvent(int currentTime) {
        byte i = 0;

        while(i < SchedulerInstance.getnbEvent() && (SchedulerInstance.incomingEvent.get(i)).getStartingTime() <= currentTime) {
            System.out.println(currentTime + " /7200");
            (SchedulerInstance.incomingEvent.get(i)).run();
            SchedulerInstance.incomingEvent.remove(i);
        }

    }

    public void passingTime() {
        Duration tick_Duration = Duration.ofMillis(1200L);
        Clock baseClock = Clock.systemUTC();
        Clock newClock = Clock.systemUTC();
        LocalTime basetime = LocalTime.now();
        LocalTime newTime = LocalTime.now();
        PrintStream var10000 = System.out;

        while(this.currentTime <= 7200) {
            basetime = LocalTime.now();
            if (basetime.isAfter(newTime)) {
                this.startingEvent(this.currentTime);
                ++this.currentTime;
                newTime = basetime.plusNanos(10000000L);
            }

            if(currentTime%10 == 0){
                WaitingList.getInstance().getSizePost().add(WaitingList.getInstance().getPostOrder().size());
                WaitingList.getInstance().getSizePre().add(WaitingList.getInstance().getPreOrder().size());
            }

            Alert alert;
            if (this.currentTime == 7200) {
                System.out.println("Fin de service");
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Fin de service");
                alert.setHeaderText("Le service est terminé");
                alert.setContentText("Vous avez servi tout les clients! Offrez un Tropico à vos kfetiers pendant que vous allez regarder les statistiques du service");
                alert.showAndWait();
            } else if (this.currentTime == 7200 && WaitingList.getInstance().getPostOrder().size() >= 1) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Fin de service");
                alert.setHeaderText("Le service est terminé mais il reste des clients a servir");
                alert.setContentText("Vous pouvez réessayer avec moins de clients ou en changeant certains paramètres (n'oubliez pas que vous simulez la K'Fet et pas le McDonalds de l'Heure Tranquille, pensez au bien-être de vos Kfetiers!)");
                alert.showAndWait();
            }
        }

        ExportExcel.CreateFile(CoreController.getInstance().getCustomers());
    }

    public static void start() {
        getInstance().passingTime();
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
