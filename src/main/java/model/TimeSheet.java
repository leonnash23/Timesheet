package model;

import event.ErrorEvent;
import event.Observers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class TimeSheet extends ArrayList<WorkDay> {

    public WorkDay getLastWorkDay() throws ErrorEvent {
        try {
            return get(size() - 1);
        } catch (IndexOutOfBoundsException e){
            throw new ErrorEvent("Work day list is empty!");
        }

    }

    public Date[] getLastPause() throws ErrorEvent {
        List<Date[]> pauses = getLastWorkDay().getPauses();
        return pauses.get(pauses.size()-1);
    }
}
