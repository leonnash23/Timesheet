import controller.Controller;
import event.Observers;
import model.TimeSheet;
import view.View;

/**
 * Created by lech0816 on 24.08.2016.
 */
public class Main {
    public static void main(String[] args) {
        Observers observers = new Observers();
        TimeSheet timeSheet = new TimeSheet();

        Controller controller = new Controller(observers,timeSheet);
        View view = new View(observers,timeSheet);

        view.start();
    }
}
