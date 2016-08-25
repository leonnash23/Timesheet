package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */
@XmlRootElement(name = "pause")
public class Pause {
    private Date start;
    private Date end;

    public Pause(){

    }

    public Pause(Date start) {
        this.start = start;
    }
    public Pause(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class )
    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }
    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class )
    public void setEnd(Date end) {
        this.end = end;
    }
}
