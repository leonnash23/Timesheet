package model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
@XmlRootElement
public class WorkDay implements Serializable {

    private Date start;
    private Date end;

    private List<Date[]> pauses;
    private Double hoursWork;

    public WorkDay(){

    }

    public WorkDay(Date start){
        this.start = start;
        this.pauses = new ArrayList<>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        WorkDay workDay = new WorkDay(start);
        workDay.setEnd(end);
        ArrayList<Date[]> pauses = new ArrayList<>();
        pauses.addAll(this.pauses);
        workDay.setPauses(pauses);
        return workDay;
    }

    public Date getStart() {
        return start;
    }
    @XmlAttribute(name = "start", required = true)
    @XmlJavaTypeAdapter( DateAdapter.class )
    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }
    @XmlAttribute(name = "end", required = true)
    @XmlJavaTypeAdapter( DateAdapter.class )
    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Date[]> getPauses() {
        return pauses;
    }
    @XmlElements({
            @XmlElement(name="pause", type= Date.class)
    })
    @XmlElementWrapper(name = "pauses")
    public void setPauses(List<Date[]> pauses) {
        this.pauses = pauses;
    }

    public Double getHoursWork() {
        return hoursWork;
    }
    @XmlAttribute
    public void setHoursWork(Double hoursWorked) {
        this.hoursWork = hoursWorked;
    }
}
