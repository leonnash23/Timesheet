package model;

import event.ErrorEvent;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
@XmlRootElement(name = "Data")
@XmlSeeAlso({WorkDay.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSheet extends ArrayList<WorkDay> {

    public WorkDay getLastWorkDay() throws ErrorEvent {
        try {
            return get(size() - 1);
        } catch (IndexOutOfBoundsException e){
            throw new ErrorEvent("Work day list is empty!");
        }

    }

    public Pause getLastPause() throws ErrorEvent {
        Pauses pauses = getLastWorkDay().getPauses();
        return pauses.get(pauses.size()-1);
    }
    @XmlElement(name = "WorkDays")
    public List<WorkDay> getWork() {
        return this;
    }
}
