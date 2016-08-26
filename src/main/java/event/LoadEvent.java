package event;

/**
 * Created by lech0816 on 25.08.2016.
 */
public class LoadEvent implements Event {
    @Override
    public final EventTypes getType() {
        return EventTypes.LOAD;
    }
}
