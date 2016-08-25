package controller;

import event.*;
import model.*;

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
        Pause date;
        date = timeSheet.getLastPause();
        date.setEnd(new Date());

    }

    private void pauseWork() throws ErrorEvent {
        Pauses pauses;
            pauses = timeSheet.getLastWorkDay().getPauses();
            Pause date = new Pause(new Date());
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
                for (Pause pause : pauses) {
                    long pauseLong = pause.getEnd().getTime() - pause.getStart().getTime();
                    workLong -= pauseLong;
                }
            } else {
                Date now = new Date();
                workLong = now.getTime() - workday.getStart().getTime();
                Pauses pauses = workday.getPauses();
                for (Pause pause : pauses) {
                    long pauseLong = pause.getEnd().getTime() - pause.getStart().getTime();
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
