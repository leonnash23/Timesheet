package view;

import controller.Controller;
import event.EndEvent;
import event.Observers;
import event.PauseEvent;
import event.StartEvent;
import model.TimeSheet;
import model.WorkDay;

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
    }

    public void start(){
        Scanner sc = new Scanner(System.in);
        boolean exit=false;
        while (!exit) {
            System.out.println("Print:");
            System.out.println("0 — to exit");
            System.out.println("1 — to start work");
            System.out.println("2 — to pause work");
            System.out.println("3 — to resume work");
            System.out.println("4 — to end work");
            System.out.println("5 — to get list");

            switch (Integer.parseInt(sc.nextLine().trim())) {
                case 0:
                    exit=true;
                    break;
                case 1:
                    observers.notifyObservers(new StartEvent());
                    System.out.println("Start time:" + Controller.format.format(timeSheet.getLastWorkDay().getStart()));
                    break;
                case 2:
                    observers.notifyObservers(new PauseEvent());
                    System.out.println("Pause time:" + Controller.format.format(timeSheet.getLastPause()[0]));
                    break;
                case 3:
                    observers.notifyObservers();
                case 4:
                    observers.notifyObservers(new EndEvent());
                    System.out.println("End time:" + Controller.format.format(timeSheet.getLastWorkDay().getEnd()));
                    System.out.println("Hours work:" + timeSheet.getLastWorkDay().getHoursWork());
                    break;
                case 5:
                    printList();
                    break;

            }
        }

    }

    private void printList(){
        for(WorkDay workDay:timeSheet){
            System.out.println(Controller.format.format(workDay.getStart()));
            System.out.println(Controller.format.format(workDay.getEnd()));
            System.out.println(workDay.getHoursWork());
            System.out.println("-----------------------");
            System.out.println();
        }
    }

    public void setObservers(Observers observers) {
        this.observers = observers;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }
}
