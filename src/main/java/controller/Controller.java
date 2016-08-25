package controller;

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
            public void notifyEvent(Event o) {
                switch (o.getType()){
                    case START:
                        startWork();
                        break;
                    case PAUSE:
                        pauseWork();
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

    private void pauseWork() {
        List<Date[]> pauses = timeSheet.getLastWorkDay().getPauses();
        Date[] date = new Date[2];
        date[0] = new Date();
        pauses.add(date);
    }

    private void endWork() {
        timeSheet.getLastWorkDay().setEnd(new Date());
        calculateHoursWork();
    }
    private void calculateHoursWork(){
        timeSheet.getLastWorkDay().setHoursWork((
                timeSheet.getLastWorkDay().getEnd().getTime()
                -timeSheet.getLastWorkDay().getStart().getTime())/(1000.0*60*60));
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
