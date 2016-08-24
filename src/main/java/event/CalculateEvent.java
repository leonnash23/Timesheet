package event;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class CalculateEvent implements Event {
    @Override
    public EventTypes getType() {
        return EventTypes.CAL;
    }
}
