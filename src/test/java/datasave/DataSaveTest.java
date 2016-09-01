package datasave;

import event.ErrorEvent;
import event.LoadEvent;
import event.Observers;
import event.SaveEvent;
import model.TimeSheet;
import model.WorkDay;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Date;

/**
 * Created by lech0816 on 01.09.2016.
 */
public class DataSaveTest {

    @Test
    public void saveDataTest(){
        TimeSheet timeSheet = new TimeSheet();
        Observers observers = new Observers();
        DataSave save = new DataSave(timeSheet,observers);
        timeSheet.add(new WorkDay(new Date(0)));
        try {
            observers.notifyObservers(new SaveEvent());
        } catch (ErrorEvent errorEvent) {
            errorEvent.printStackTrace();
        }
        File file = new File("data.xml");
        Assert.assertTrue(file.exists());


        timeSheet = new TimeSheet();
        observers = new Observers();
        save = new DataSave(timeSheet,observers);
        try {
            observers.notifyObservers(new LoadEvent());
        } catch (ErrorEvent errorEvent) {
            errorEvent.printStackTrace();
        }

        try {
            Assert.assertEquals(timeSheet.getLastWorkDay().getStart().getTime(),0);
        } catch (ErrorEvent errorEvent) {
            errorEvent.printStackTrace();
        }

    }
}
