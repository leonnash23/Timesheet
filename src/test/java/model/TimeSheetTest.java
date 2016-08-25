package model;

import event.ErrorEvent;
import event.Observer;
import event.Observers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lech0816 on 25.08.2016.
 */
public class TimeSheetTest {
    @Test
    public void getLastWorkDayTest(){
        TimeSheet timeSheet = new TimeSheet();
        Observers observers = new Observers();
        timeSheet.add(new WorkDay(new Date(1)));
        timeSheet.add(new WorkDay(new Date(2)));
        timeSheet.add(new WorkDay(new Date(3)));
        timeSheet.add(new WorkDay(new Date(4)));
        timeSheet.add(new WorkDay(new Date(5)));
        timeSheet.add(new WorkDay(new Date(6)));
        try {
            Assert.assertEquals(timeSheet.getLastWorkDay().getStart().getTime(),6);
            Assert.assertNull(timeSheet.getLastWorkDay().getEnd());
        } catch (ErrorEvent errorEvent) {
            try {
                observers.notifyObservers(errorEvent);
            } catch (ErrorEvent errorEvent1) {
                errorEvent1.printStackTrace();
            }
        }

    }
    @Test
    public void getLastPauseTest(){
        TimeSheet timeSheet = new TimeSheet();
        Observers observers = new Observers();
        timeSheet.add(new WorkDay(new Date(1)));
        timeSheet.add(new WorkDay(new Date(2)));
        timeSheet.add(new WorkDay(new Date(3)));
        timeSheet.add(new WorkDay(new Date(4)));
        Pauses pauses = new Pauses();
        pauses.add(new MyDate[]{new MyDate(5),new MyDate(6)});
        try {
            timeSheet.getLastWorkDay().setPauses(pauses);
            Assert.assertEquals(timeSheet.getLastPause()[0].getTime(),5);
        } catch (ErrorEvent errorEvent) {
            try {
                observers.notifyObservers(errorEvent);
            } catch (ErrorEvent errorEvent1) {
                errorEvent1.printStackTrace();
            }
        }

    }
}
