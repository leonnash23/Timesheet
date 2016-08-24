package controller;

import event.CalculateEvent;
import event.Observers;
import event.StartEvent;
import model.TimeSheet;
import model.WorkDay;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class ControllerTest {
    @Test
    public void startWorkTest(){
        Observers observers = new Observers();
        TimeSheet timeSheet = new TimeSheet();
        Controller controller = new Controller(observers,timeSheet);
        observers.notifyObservers(new StartEvent());
        Assert.assertEquals(Controller.format.format(timeSheet.getLast().getStart()),
                Controller.format.format(new Date()));
    }

    @Test
    public void endWorkTest(){
        long start = 0;
        long end = 28800000;
        Observers observers = new Observers();
        TimeSheet timeSheet = new TimeSheet();
        Controller controller = new Controller(observers,timeSheet);
        timeSheet.add(new WorkDay(new Date(start)));
        timeSheet.getLast().setEnd(new Date(end));
        observers.notifyObservers(new CalculateEvent());
        Assert.assertEquals(Double.compare(8.0,timeSheet.getLast().getHoursWork()),0);
    }
}
