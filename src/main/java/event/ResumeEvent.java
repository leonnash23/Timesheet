package event;

/**
 * Created by lech0816 on 25.08.2016.
 */
public class ResumeEvent implements Event {
    @Override
    public EventTypes getType() {
        return EventTypes.RESUME;
    }
}
