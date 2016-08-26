package model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lech0816 on 24.08.2016.
 */
@XmlRootElement
@XmlType(propOrder = {"start", "end", "hoursWork", "pauses"})
@XmlSeeAlso({Date.class, Pauses.class})
public class WorkDay implements Serializable {

    private Date start;
    private Date end;

    private Pauses pauses;
    private Double hoursWork;

    public WorkDay() {

    }

    public WorkDay(final Date start) {
        this.start = start;
        this.pauses = new Pauses();
    }

//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        WorkDay workDay = new WorkDay(start);
//        workDay.setEnd(end);
//        ArrayList<Date[]> pauses = new ArrayList<>();
//        pauses.addAll(this.pauses);
//        workDay.setPauses(pauses);
//        return workDay;
//    }
public final Pauses getPauses() {
    return pauses;
}


    @XmlElements({
            @XmlElement(name = "pause", type = Pause.class)
    })
    @XmlElementWrapper(name = "pauses")
    public final void setPauses(final Pauses pauses) {
        this.pauses = pauses;
    }

    public final Date getEnd() {
        return end;
    }
    @XmlElement(name = "end", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final void setEnd(final Date end) {
        this.end = end;
    }

    public final Date getStart() {
        return start;
    }
    @XmlElement(name = "start", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final void setStart(final Date start) {
        this.start = start;
    }





    public final Double getHoursWork() {
        return hoursWork;
    }
    @XmlElement
    public final void setHoursWork(final Double hoursWorked) {
        this.hoursWork = hoursWorked;
    }
}
