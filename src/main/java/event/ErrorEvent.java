package event;

/**
 * Created by lech0816 on 25.08.2016.
 */
public class ErrorEvent extends Exception implements Event {

    public ErrorEvent(){
        super();
    }
    public ErrorEvent(String message){
        super(message);
    }

    @Override
    public EventTypes getType() {
        return EventTypes.ERROR;
    }
}
