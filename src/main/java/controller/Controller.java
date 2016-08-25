package controller;

import event.*;
import model.MyDate;
import model.Pauses;
import model.TimeSheet;
import model.WorkDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class Controller {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd EEEE HH:mm");

    private Observers observers;
    private TimeSheet timeSheet;

    public Controller(final Observers observers, TimeSheet timeSheet){
        this.observers = observers;
        this.timeSheet = timeSheet;

        observers.addListners(new Observer() {
            @Override
            public void notifyEvent(Event o) throws ErrorEvent {
                switch (o.getType()){
                    case START:
                        startWork();
                        observers.notifyObservers(new SaveEvent());
                        break;
                    case PAUSE:
                        pauseWork();
                        observers.notifyObservers(new SaveEvent());
                        break;
                    case RESUME:
                        resumeWork();
                        observers.notifyObservers(new SaveEvent());
                        break;
                    case END:
                        endWork();
                        observers.notifyObservers(new SaveEvent());
                        break;
                    case CAL:
                        calculateHoursWork();
                        break;
                }

            }
        });
    }

    private void resumeWork() throws ErrorEvent {
        MyDate[] date;
        date = timeSheet.getLastPause();
        date[1] = new MyDate();

    }

    private void pauseWork() throws ErrorEvent {
        Pauses pauses;
            pauses = timeSheet.getLastWorkDay().getPauses();
            MyDate[] date = new MyDate[2];
            date[0] = new MyDate();
            pauses.add(date);

    }

    private void endWork() throws ErrorEvent {
            timeSheet.getLastWorkDay().setEnd(new Date());
            calculateHoursWork();

    }
    private void calculateHoursWork() throws ErrorEvent {
        WorkDay workday;

            workday = timeSheet.getLastWorkDay();
            long workLong;
            if(workday.getEnd()!=null) {
                workLong = workday.getEnd().getTime() - workday.getStart().getTime();
                Pauses pauses = workday.getPauses();
                for (Date[] pause : pauses) {
                    long pauseLong = pause[1].getTime() - pause[0].getTime();
                    workLong -= pauseLong;
                }
            } else {
                Date now = new Date();
                workLong = now.getTime() - workday.getStart().getTime();
                Pauses pauses = workday.getPauses();
                for (Date[] pause : pauses) {
                    long pauseLong = pause[1].getTime() - pause[0].getTime();
                    workLong -= pauseLong;
                }
            }
            workday.setHoursWork(workLong/(1000.0*60*60));


    }
    private void startWork() {
        timeSheet.add(new WorkDay(new Date()));
    }

    public void setObservers(Observers observers) {
        this.observers = observers;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }


}
