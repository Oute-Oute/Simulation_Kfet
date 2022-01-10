package main.java.control;

import javafx.scene.control.Alert;
import kfet.CoreController;
import main.java.Event;
import main.java.WaitingList;
import main.java.report.ExportExcel;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public final class Scheduler {
    private static Scheduler SchedulerInstance = new Scheduler();

    private ArrayList<Event> incomingEvent;
    private int currentTime; //Temps en secondes => simulation se finit au bout de 2h donc 7200 secondes

    private Scheduler(){
        currentTime = 0;
        incomingEvent = new ArrayList<>();
    }

    public static Scheduler getInstance() {
        if (SchedulerInstance == null) {
            SchedulerInstance = new Scheduler();
        }
        return SchedulerInstance;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getnbEvent(){
        return incomingEvent.size();
    }

    /**
     * Ajoute l'event à la liste d'event à lancer en la triant par ordre chronologique croissant
     * @param event Evenement a ajouter
     */
    public void addEvent(Event event){
        if (SchedulerInstance.incomingEvent.isEmpty()){   //Si la liste est vide on ajoute juste l'élément
            SchedulerInstance.incomingEvent.add(event);
        } else {                        //Si elle n'est pas vide on doit ajouter à la bonne place
            boolean found = false;
            int i = 0;
            while(i < SchedulerInstance.incomingEvent.size() && !found){
                if(event.getStartingTime() < SchedulerInstance.incomingEvent.get(i).getStartingTime()){  //On cherche le premier event de la liste au temps superieur a celui qu'on ajoute
                    SchedulerInstance.incomingEvent.add(i, event);
                    found = true;
                }
                i++;
            }
            if(i == incomingEvent.size() && !found){        //Si on rentre dans ce if, c'est que l'event qu'on ajoute a le + grand temps de la liste
                incomingEvent.add(i, event);
            }
        }
    }

    /**
     * execute les méthodes de tous les events censés commencer à l'heure actuelle
     * @param currentTime the current time
     */
    public void startingEvent(int currentTime) {
        int i = 0;

        while(i < SchedulerInstance.getnbEvent() && SchedulerInstance.incomingEvent.get(i).getStartingTime() <= currentTime){
            System.out.println(currentTime+" /7200");
            SchedulerInstance.incomingEvent.get(i).run();
            SchedulerInstance.incomingEvent.remove(i);
            //i++;
        }
    }

    public void passingTime() {
        Duration tick_Duration = Duration.ofMillis(1200);     // Une seconde dans la simulation = 0.084 secondes IRL pour que le service de 2h soit simulé en 10 minutes
        Clock baseClock = Clock.systemUTC();
        Clock newClock = Clock.systemUTC();
        LocalTime basetime = LocalTime.now();
        LocalTime newTime = LocalTime.now();

        System.out.println(ControllerDevices.getInstance().getFreeDevices().get("Cafetiere")+" cafetieres de libre");

        while (currentTime <= 7200) {
            basetime=LocalTime.now();
            //if (basetime.isAfter(newTime)) {
                if (baseClock.instant().truncatedTo(ChronoUnit.SECONDS).equals(newClock.instant().truncatedTo(ChronoUnit.SECONDS))) {//exec rapide
                newTime=basetime;
                startingEvent(currentTime);
                currentTime += 1;
                newTime=newTime.plusNanos(10000000);

                //System.out.println(Scheduler.getInstance().getCurrentTime());
                newClock = Clock.tickSeconds(ZoneId.systemDefault());//execrapide

            }
            if (currentTime==7200){
                System.out.println("Fin de service");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Fin de service");
                alert.setHeaderText("Le service est terminé");
                alert.setContentText("Vous avez servi tout les clients! Offrez un Tropico à vos kfetiers pendant que vous allez regarder les statistiques du service");
                alert.showAndWait();
            }
            if (currentTime ==7200 && WaitingList.getInstance().getPostOrder().size()>=1){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Fin de service");
                alert.setHeaderText("Le service est terminé mais il reste des clients a servir");
                alert.setContentText("Vous pouvez réessayer avec moins de clients ou en changeant certains paramètres (n'oubliez pas que vous simulez la K'Fet et pas le McDonalds de l'Heure Tranquille, pensez au bien-être de vos Kfetiers!)");
                alert.showAndWait();
            }
        }

        ExportExcel.CreateFile(CoreController.getInstance().getCustomers());
    }


    //TODO: ne fonctionne pas si on essaye de relancer la simulation
    public static void start(){
        getInstance().passingTime();
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
