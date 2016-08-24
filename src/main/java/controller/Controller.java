package controller;

import event.Event;
import event.Observer;
import event.Observers;
import model.TimeSheet;
import model.WorkDay;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private void endWork() {
        timeSheet.getLast().setEnd(new Date());
        calculateHoursWork();
    }
    private void calculateHoursWork(){
        timeSheet.getLast().setHoursWork((
                timeSheet.getLast().getEnd().getTime()
                -timeSheet.getLast().getStart().getTime())/(1000.0*60*60));
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
