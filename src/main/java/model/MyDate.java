package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */

public class MyDate extends Date {
    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class )
    public Date date = this;
    public MyDate(){
        super();
    }

    public MyDate(long mil){
        super(mil);
    }
}
