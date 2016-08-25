package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class TimeSheet extends ArrayList<WorkDay> {
    public WorkDay getLastWorkDay(){
        return (WorkDay) get(size()-1);
    }

    public Date[] getLastPause(){
        List<Date[]> pauses = getLastWorkDay().getPauses();
        return pauses.get(pauses.size()-1);
    }
}
