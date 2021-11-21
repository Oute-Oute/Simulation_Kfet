package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;

public class Event {
    private static ArrayList<Event> incomingEvent = new ArrayList<>();
    private static int currentTime = 0; //Temps en secondes => simulation se finit au bout de 2h donc 7200 secondes

    private int startingTime;
    private Method event;
    private ArrayList<Object> parameters;

    /**
     * Constructeur
     * @param startingTime date de début de l'event
     * @param event méthode à lancer
     */
    public Event(int startingTime, Method event, ArrayList<Object> parameters) {
        this.startingTime = startingTime;
        this.event = event;
        this.parameters = parameters;
    }

    /**
     * getter de CurrentTime
     * @return CurrentTime
     */
    public static int getCurrentTime() {
        return currentTime;
    }

    /**
     * Ajoute l'event à la liste d'event à lancer en la triant par ordre chronologique croissant
     */
    public void addEvent(){
        if (incomingEvent.isEmpty()){   //Si la liste est vide on ajoute juste l'élément
            incomingEvent.add(this);
        } else {                        //Si elle n'est pas vide on doit ajouter à la bonne place
            boolean found = false;
            int i = 0;
            while(i < incomingEvent.size() && !found){
                if(this.startingTime < incomingEvent.get(i).startingTime){  //On cherche le premier event de la liste au temps superieur a celui qu'on ajoute
                    incomingEvent.add(i, this);
                    found = true;
                }
                i++;
            }
            if(i == incomingEvent.size() && !found){        //Si on rentre dans ce if, c'est que l'event qu'on ajoute a le + grand temps de la liste
                incomingEvent.add(i, this);
            }
        }
    }

    /**
     * execute les méthodes de tous les events censés commencer à l'heure actuelle
     * @param currentTime
     */
    public void startingEvent(int currentTime) throws InvocationTargetException, IllegalAccessException {
        int i = 0;

        while(incomingEvent.get(i).startingTime <= currentTime){
            Event evenement = incomingEvent.get(i);
            evenement.event.invoke(evenement.getClass(),evenement.parameters);
            i++;
        }

    }

    public void passingTime() throws InvocationTargetException, IllegalAccessException {
        Duration tick_Duration = Duration.ofMillis(84);     // Une seconde dans la simulation = 0.084 secondes IRL pour que le service de 2h soit simulé en 10 minutes
        Clock clock = Clock.systemUTC();
        Clock newClock = Clock.tick(clock, tick_Duration);

        while(currentTime != 7200)
        if (!clock.equals(newClock)){
            clock = newClock;
            startingEvent(currentTime);
            currentTime += 1;
        }
    }

}
