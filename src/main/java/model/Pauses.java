package model;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */
@XmlSeeAlso(MyDate.class)
public class Pauses extends ArrayList<MyDate[]> {

}
