package model;

import controller.Controller;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */
class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat formatter = Controller.FORMAT;

    @Override
    public Date unmarshal(final String v) throws Exception {
        if (!v.equals("null")) {
            Date parse = formatter.parse(v);
            return new Date(parse.getTime());
        } else {
            return null;
        }
    }

    @Override
    public String marshal(final Date v) throws Exception {
        if (v != null) {
            return formatter.format(v);
        } else {
            return null;
        }
    }
}
