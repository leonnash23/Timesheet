package controller;

import event.ErrorEvent;
import event.Event;
import event.Observer;
import event.Observers;
import model.TimeSheet;
import model.WorkDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class Controller {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    private Observers observers;
    private TimeSheet timeSheet;

    public Controller(Observers observers, TimeSheet timeSheet){
        this.observers = observers;
        this.timeSheet = timeSheet;

        observers.addListners(new Observer() {
            @Override
            public void notifyEvent(Event o) throws ErrorEvent {
                switch (o.getType()){
                    case START:
                        startWork();
                        break;
                    case PAUSE:
                        pauseWork();
                        break;
                    case RESUME:
                        resumeWork();
                        break;
                    case END:
                        endWork();
                        break;
                    case CAL:
                        calculateHoursWork();
                        break;
                }

            }
        });
    }

    private void resumeWork() throws ErrorEvent {
        Date[] date;
        date = timeSheet.getLastPause();
        date[1] = new Date();

    }

    private void pauseWork() throws ErrorEvent {
        List<Date[]> pauses;
            pauses = timeSheet.getLastWorkDay().getPauses();
            Date[] date = new Date[2];
            date[0] = new Date();
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
                List<Date[]> pauses = workday.getPauses();
                for (Date[] pause : pauses) {
                    long pauseLong = pause[1].getTime() - pause[0].getTime();
                    workLong -= pauseLong;
                }
            } else {
                Date now = new Date();
                workLong = now.getTime() - workday.getStart().getTime();
                List<Date[]> pauses = workday.getPauses();
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
