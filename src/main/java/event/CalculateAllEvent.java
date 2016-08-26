package event;

/**
 * Created by lech0816 on 26.08.2016.
 */
public class CalculateAllEvent implements Event {
    @Override
    public final EventTypes getType() {
        return EventTypes.CALALL;
    }
}
