package controller;

import event.Observers;
import event.Observer;
import event.ErrorEvent;
import event.Event;
import event.SaveEvent;

import model.Pause;
import model.Pauses;
import model.TimeSheet;
import model.WorkDay;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class Controller {
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy.MM.dd EEEE HH:mm");
    private final Double CONVERT_MILLISECONDS_TO_HOURS = 1000.0 * 60 * 60;

    private Observers observers;
    private TimeSheet timeSheet;

    public Controller(final Observers observers, TimeSheet timeSheet) {
        this.observers = observers;
        this.timeSheet = timeSheet;

        observers.addListners(new Observer() {
            @Override
            public void notifyEvent(final Event o) throws ErrorEvent {
                switch (o.getType()) {
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
                        calculateHoursWorkLast();
                        break;
                    case CALALL:
                        calculateHoursWorkAll();
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
            calculateHoursWorkLast();

    }

    private void calculateHoursWork(final WorkDay workDay) throws ErrorEvent {
            long workLong = 0;
            if (workDay.getEnd() != null) {
                workLong = workDay.getEnd().getTime() - workDay.getStart().getTime();
                Pauses pauses = workDay.getPauses();
                for (Pause pause : pauses) {
                    long pauseLong = pause.getEnd().getTime() - pause.getStart().getTime();
                    workLong -= pauseLong;
                }
            } else if (workDay == timeSheet.getLastWorkDay()) {
                workLong = new Date().getTime() - workDay.getStart().getTime();
                Pauses pauses = workDay.getPauses();
                for (Pause pause : pauses) {
                    long pauseLong = new Date().getTime() - pause.getStart().getTime();
                    workLong -= pauseLong;
                }
            }
        workDay.setHoursWork(workLong / CONVERT_MILLISECONDS_TO_HOURS);
    }

    private void calculateHoursWorkLast() throws ErrorEvent {
        calculateHoursWork(timeSheet.getLastWorkDay());
    }

    private void calculateHoursWorkAll() throws ErrorEvent {
        for (WorkDay workDay:timeSheet) {
            if (workDay == timeSheet.getLastWorkDay()) {
                continue;
            }
            calculateHoursWork(workDay);
        }
        observers.notifyObservers(new SaveEvent());
    }

    private void startWork() throws ErrorEvent {
        if (timeSheet.size()>0 && timeSheet.getLastWorkDay().getEnd() == null) {
            throw new ErrorEvent("Last work day not fineshed!");
        }
        timeSheet.add(new WorkDay(new Date()));
    }

    public void setObservers(final Observers observers) {
        this.observers = observers;
    }

    public void setTimeSheet(final TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }


}
