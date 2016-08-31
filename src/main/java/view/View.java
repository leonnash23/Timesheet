package view;

import controller.Controller;
import event.Observer;
import event.Observers;
import event.EventTypes;
import event.Event;
import event.LoadEvent;
import event.CalculateAllEvent;
import event.ErrorEvent;
import event.StartEvent;
import event.PauseEvent;
import event.ResumeEvent;
import event.EndEvent;
import event.CalculateEvent;
import model.Pause;
import model.TimeSheet;
import model.WorkDay;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class View {

    private final double MILLISECONDS_IN_MINUTES = 60000.0;
    private final long MILLISECONDS_IN_MINUTES_LONG = 60000;
    private final long MILLISECONDS_IN_HOURS = (long) (MILLISECONDS_IN_MINUTES*60);
    private final long EIGHT_HOURS_IN_MILLISECONDS = 8 * 60 * 60 * 1000;

    private Observers observers;
    private TimeSheet timeSheet;

    public View(final Observers observers, final TimeSheet timeSheet) {
        this.observers = observers;
        this.timeSheet = timeSheet;
        this.observers.addListners(new Observer() {
            @Override
            public void notifyEvent(final Event o) {
                if (o.getType() == EventTypes.ERROR) {
                    printError(((Exception) o).getMessage());
                }
            }
        });
    }

    private void printError(final String message) {
        System.err.println("Error:" + message);
    }

    public final void start() {
        try {
            observers.notifyObservers(new LoadEvent());
            observers.notifyObservers(new CalculateAllEvent());
        } catch (ErrorEvent errorEvent) {
            errorEvent.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Print:");
            System.out.println("\t0 — to exit");
            System.out.println("\t1 — to get hours from last start or last work day");
            System.out.println("\t2 — to start work");
            System.out.println("\t3 — to pause work");
            System.out.println("\t4 — to get today pause list");
            System.out.println("\t5 — to resume work");
            System.out.println("\t6 — to end work");
            System.out.println("\t7 — to get list");
            try {
                switch (Integer.parseInt(sc.nextLine().trim())) {
                    case 0:
                        exit = true;
                        break;
                    case 1:
                        printCalculateTime();
                        break;
                    case 2:
                        observers.notifyObservers(new StartEvent());
                        System.out.println("Start time:"
                                + Controller.FORMAT.format(timeSheet.getLastWorkDay().getStart()));
                        break;
                    case 3:
                        observers.notifyObservers(new PauseEvent());
                        System.out.println("Pause time:"
                                + Controller.FORMAT.format(timeSheet.getLastPause().getStart()));
                        break;
                    case 4:
                        printPauseList();
                        break;
                    case 5:
                        observers.notifyObservers(new ResumeEvent());
                        System.out.println("Pause time:"
                                + Controller.FORMAT.format(timeSheet.getLastPause().getEnd()));
                        Pause pause = timeSheet.getLastPause();
                        System.out.println("Pause long:"
                                + (pause.getEnd().getTime() - pause.getStart().getTime()) / MILLISECONDS_IN_MINUTES + " minutes");
                        break;
                    case 6:
                        observers.notifyObservers(new EndEvent());
                        System.out.println("End time:"
                                + Controller.FORMAT.format(timeSheet.getLastWorkDay().getEnd()));
                        System.out.println("Hours work:"
                                + timeSheet.getLastWorkDay().getHoursWork());
                        break;
                    case 7:
                        printList();
                        break;
                    default:
                        System.err.println("Unsupported command!");

                }
            } catch (NumberFormatException e) {
                System.err.println("Wrong input!");
            } catch (ErrorEvent errorEvent) {
                try {
                    observers.notifyObservers(errorEvent);
                } catch (ErrorEvent errorEvent1) {
                    errorEvent1.printStackTrace();
                }
            }
        }

    }

    private void printCalculateTime() throws ErrorEvent {
        observers.notifyObservers(new CalculateEvent());
        System.out.printf("%s%.1f%s","\n",timeSheet.getLastWorkDay().getHoursWork()," hours worked\n");
        long milis = EIGHT_HOURS_IN_MILLISECONDS - (long) (timeSheet.getLastWorkDay().getHoursWork() * MILLISECONDS_IN_HOURS);
        System.out.println("8 hours work day will finished at "
                + Controller.FORMAT.format(new Date(new Date().getTime()
                        + milis)));
    }

    private void printPauseList() throws ErrorEvent {
        WorkDay workDay = null;
            workDay = timeSheet.getLastWorkDay();
            int i = 0;
            for (Pause pause:workDay.getPauses()) {
                System.out.println("Pause #" + (++i) + ":");
                System.out.println("\tPause start:"
                        + Controller.FORMAT.format(pause.getStart()));
                if (pause.getEnd() == null) {
                    System.out.println("\tPause end: pause not fineshed yet");
                    System.out.println("\tPause long:"
                            + (new Date().getTime()
                            - pause.getStart().getTime()) / MILLISECONDS_IN_MINUTES_LONG + " minutes");
                } else {
                    System.out.println("\tPause end:"
                            + Controller.FORMAT.format(pause.getEnd()));
                    System.out.println("\tPause long:"
                            + (pause.getEnd().getTime() - pause.getStart().getTime())
                            / MILLISECONDS_IN_MINUTES_LONG + " minutes");
                }

                System.out.println();
            }


    }

    private void printList() {
        if (timeSheet.size() == 0) {
            System.out.println("List is empty.");
        } else {
            for (WorkDay workDay : timeSheet) {
                System.out.println("Started at: "+Controller.FORMAT.format(workDay.getStart()));
                if (workDay.getEnd() == null) {
                    System.out.println("Work day not finished yet.");
                } else {
                    System.out.println("Ended at: "+Controller.FORMAT.format(workDay.getEnd()));
                }
                System.out.println("Hours worked: "+workDay.getHoursWork());
                System.out.println("-----------------------");
                System.out.println();
            }
        }
    }

}
