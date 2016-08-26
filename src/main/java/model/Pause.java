package model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;

import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */
@XmlRootElement(name = "pause")
@XmlType(propOrder = {"start", "end"})
public class Pause {
    private Date start;
    private Date end;

    public Pause() {

    }

    public Pause(final Date start) {
        this.start = start;
    }
    public Pause(final Date start, final Date end) {
        this.start = start;
        this.end = end;
    }

    public final Date getEnd() {
        return end;
    }
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final void setEnd(final Date end) {
        this.end = end;
    }

    public final Date getStart() {
        return start;
    }
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public final void setStart(final Date start) {
        this.start = start;
    }


}
