package event;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class EndEvent implements Event {
    @Override
    public final EventTypes getType() {
        return EventTypes.END;
    }
}
