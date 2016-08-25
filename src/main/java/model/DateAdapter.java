package model;

import controller.Controller;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */
class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat formatter = Controller.format;

    @Override
    public Date unmarshal(String v) throws Exception {
        if(!v.equals("null")) {
            java.util.Date parse = formatter.parse(v);
            return new Date(parse.getTime());
        } else {
            return null;
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        if(v!=null) {
            return formatter.format(v);
        }else {
            return null;
        }
    }
}
