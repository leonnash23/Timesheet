package event;

/**
 * Created by lech0816 on 24.08.2016.
 */
public interface Observer {
    void notifyEvent(Event o) throws ErrorEvent;
}
