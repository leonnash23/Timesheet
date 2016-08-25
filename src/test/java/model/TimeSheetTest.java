package model;

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
        timeSheet.add(new WorkDay(new Date(1)));
        timeSheet.add(new WorkDay(new Date(2)));
        timeSheet.add(new WorkDay(new Date(3)));
        timeSheet.add(new WorkDay(new Date(4)));
        timeSheet.add(new WorkDay(new Date(5)));
        timeSheet.add(new WorkDay(new Date(6)));
        Assert.assertEquals(timeSheet.getLastWorkDay().getStart().getTime(),6);
        Assert.assertNull(timeSheet.getLastWorkDay().getEnd());
    }
    @Test
    public void getLastPauseTest(){
        TimeSheet timeSheet = new TimeSheet();
        timeSheet.add(new WorkDay(new Date(1)));
        timeSheet.add(new WorkDay(new Date(2)));
        timeSheet.add(new WorkDay(new Date(3)));
        timeSheet.add(new WorkDay(new Date(4)));
        ArrayList<Date[]> pauses = new ArrayList<>();
        pauses.add(new Date[]{new Date(5),new Date(6)});
        timeSheet.getLastWorkDay().setPauses(pauses);
        Assert.assertEquals(timeSheet.getLastPause()[0].getTime(),5);
    }
}
