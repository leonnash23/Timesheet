package view;

import controller.Controller;
import event.*;
import model.TimeSheet;
import model.WorkDay;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class View {

    private Observers observers;
    private TimeSheet timeSheet;

    public View(Observers observers,TimeSheet timeSheet){
        this.observers = observers;
        this.timeSheet = timeSheet;
        this.observers.addListners(new Observer() {
            @Override
            public void notifyEvent(Event o) {
                if(o.getType()==EventTypes.ERROR){
                    printError(((Exception)o).getMessage());
                }
            }
        });
    }

    private void printError(String message) {
        System.err.println("Error:"+message);
    }

    public void start(){
        Scanner sc = new Scanner(System.in);
        boolean exit=false;
        while (!exit) {
            System.out.println("Print:");
            System.out.println("0 — to exit");
            System.out.println("1 — to get hours from last start or last work day");
            System.out.println("2 — to start work");
            System.out.println("3 — to pause work");
            System.out.println("4 — to get today pause list");
            System.out.println("5 — to resume work");
            System.out.println("6 — to end work");
            System.out.println("7 — to get list");
            try {
                switch (Integer.parseInt(sc.nextLine().trim())) {
                    case 0:
                        exit = true;
                        break;
                    case 1:
                        observers.notifyObservers(new CalculateEvent());
                        System.out.println(timeSheet.getLastWorkDay().getHoursWork() + " hours worked");
                        break;
                    case 2:
                        observers.notifyObservers(new StartEvent());
                        System.out.println("Start time:" + Controller.format.format(timeSheet.getLastWorkDay().getStart()));
                        break;
                    case 3:
                        observers.notifyObservers(new PauseEvent());
                        System.out.println("Pause time:" + Controller.format.format(timeSheet.getLastPause()[0]));
                        break;
                    case 4:
                        printPauseList();
                        break;
                    case 5:
                        observers.notifyObservers(new ResumeEvent());
                        System.out.println("Pause time:" + Controller.format.format(timeSheet.getLastPause()[1]));
                        Date[] pause = timeSheet.getLastPause();
                        System.out.println("Pause long:" + (pause[1].getTime() - pause[0].getTime()) / 60000.0 + " minutes");
                        break;
                    case 6:
                        observers.notifyObservers(new EndEvent());
                        System.out.println("End time:" + Controller.format.format(timeSheet.getLastWorkDay().getEnd()));
                        System.out.println("Hours work:" + timeSheet.getLastWorkDay().getHoursWork());
                        break;
                    case 7:
                        printList();
                        break;
                    default:
                        System.err.println("Unsupported command!");

                }
            } catch (NumberFormatException e){
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

    private void printPauseList() throws ErrorEvent {
        WorkDay workDay = null;
            workDay = timeSheet.getLastWorkDay();
            int i =0;
            for(Date[] pause:workDay.getPauses()){
                System.out.println("Pause #"+(++i)+":");
                System.out.println("\tPause start:"+Controller.format.format(pause[0]));
                if(pause[1]==null){
                    System.out.println("\tPause end: pause not fineshed yet");
                    System.out.println("\tPause long:"+(new Date().getTime()-pause[0].getTime())/60000+" minutes");
                } else {
                    System.out.println("\tPause end:"+Controller.format.format(pause[1]));
                    System.out.println("\tPause long:"+(pause[1].getTime()-pause[0].getTime())/60000+" minutes");
                }

                System.out.println();
            }


    }

    private void printList(){
        if(timeSheet.size()==0){
            System.out.println("List is empty.");
        } else {
            for (WorkDay workDay : timeSheet) {
                System.out.println(Controller.format.format(workDay.getStart()));
                if (workDay.getEnd() == null) {
                    System.out.println("Work day not finished yet.");
                } else {
                    System.out.println(Controller.format.format(workDay.getEnd()));
                }
                System.out.println(workDay.getHoursWork());
                System.out.println("-----------------------");
                System.out.println();
            }
        }
    }

    public void setObservers(Observers observers) {
        this.observers = observers;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }
}
