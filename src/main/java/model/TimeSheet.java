package model;

import event.ErrorEvent;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
@XmlRootElement(name = "Data")
@XmlSeeAlso({WorkDay.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeSheet extends ArrayList<WorkDay> {

    public final WorkDay getLastWorkDay() throws ErrorEvent {
        try {
            return get(size() - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ErrorEvent("Work day list is empty!");
        }

    }

    public final Pause getLastPause() throws ErrorEvent {
        Pauses pauses = getLastWorkDay().getPauses();
        return pauses.get(pauses.size() - 1);
    }
    @XmlElement(name = "WorkDays")
    public final List<WorkDay> getWork() {
        return this;
    }
}
