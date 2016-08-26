package event;

import java.util.ArrayList;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class Observers<T extends Observer> extends ArrayList<T> {
    public final void notifyObservers(final Event o) throws ErrorEvent {
        for (T t : this) {
            t.notifyEvent(o);
        }
    }
    public final void addListners(final T observer) {
        add(observer);
    }
}
