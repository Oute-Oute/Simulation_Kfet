package main.java.control;

import main.java.Event;

import java.time.Clock;
import java.time.Duration;
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
        if (SchedulerInstance == null){
            SchedulerInstance = new Scheduler();
        }
        return SchedulerInstance;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Ajoute l'event à la liste d'event à lancer en la triant par ordre chronologique croissant
     * @param event Evenement a ajouter
     */
    public void addEvent(Event event){
        if (incomingEvent.isEmpty()){   //Si la liste est vide on ajoute juste l'élément
            incomingEvent.add(event);
        } else {                        //Si elle n'est pas vide on doit ajouter à la bonne place
            boolean found = false;
            int i = 0;
            while(i < incomingEvent.size() && !found){
                if(event.getStartingTime() < incomingEvent.get(i).getStartingTime()){  //On cherche le premier event de la liste au temps superieur a celui qu'on ajoute
                    incomingEvent.add(i, event);
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

        while(incomingEvent.get(i).getStartingTime() <= currentTime){
            incomingEvent.get(i).run();
            i++;
        }
    }

    public void passingTime() {
        Duration tick_Duration = Duration.ofMillis(1200);     // Une seconde dans la simulation = 0.084 secondes IRL pour que le service de 2h soit simulé en 10 minutes
        Clock baseClock = Clock.systemUTC();
        Clock newClock = Clock.systemUTC();

        while (currentTime <= 7200) {

            if (baseClock.instant().truncatedTo(ChronoUnit.SECONDS).equals(newClock.instant().truncatedTo(ChronoUnit.SECONDS))) {
                baseClock = newClock;
                startingEvent(currentTime);
                currentTime += 1;
                newClock = Clock.tickSeconds(ZoneId.systemDefault());
            }
        }
    }

    public static void start(){
        Scheduler sch = new Scheduler();
        sch.passingTime();
    }

    public static void main(String[] args){
        Scheduler sch = new Scheduler();
        sch.passingTime();
    }
}
