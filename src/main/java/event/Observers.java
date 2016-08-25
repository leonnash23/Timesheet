package event;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class Observers<T extends Observer> extends ArrayList<T> {
    public void notifyObservers(Event o) throws ErrorEvent {
        for(Iterator<T> iterator = (Iterator<T>)iterator();iterator.hasNext();){
            iterator.next().notifyEvent(o);
        }
    }
    public void addListners(T observer){
        add(observer);
    }
}
