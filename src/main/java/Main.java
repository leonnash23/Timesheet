import controller.Controller;
import datasave.DataSave;
import event.Observers;
import model.TimeSheet;
import view.View;

/**
 * Created by lech0816 on 24.08.2016.
 */
public final class Main {

    private Main() {

    }

    public static void main(final String[] args) {
        Observers observers = new Observers();
        TimeSheet timeSheet = new TimeSheet();
        DataSave dataSave = new DataSave(timeSheet, observers);

        Controller controller = new Controller(observers, timeSheet);
        View view = new View(observers, timeSheet);

        view.start();
    }
}
