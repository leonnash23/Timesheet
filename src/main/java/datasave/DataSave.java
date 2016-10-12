package datasave;

import event.*;
import model.TimeSheet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXB;
import java.io.File;

/**
 * Created by lech0816 on 25.08.2016.
 */
public class DataSave {
    private TimeSheet timeSheet;
    private final String FILE_NAME = "data.xml";
    private Observers observers;

    public DataSave(final TimeSheet timeSheet, final Observers observers) {
        this.timeSheet = timeSheet;
        this.observers = observers;
        observers.addListners(new Observer() {
            @Override
            public void notifyEvent(Event o) throws ErrorEvent {
                if (o.getType() == EventTypes.SAVE) {
                    saveData();
                } else if (o.getType() == EventTypes.LOAD) {
                    loadData();
                }
            }
        });
    }

    private void saveData() throws ErrorEvent {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TimeSheet.class);
            Marshaller jaxbMarshaller;
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(timeSheet, new File(FILE_NAME));
        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws ErrorEvent {
        File file = new File(FILE_NAME);
        if (file.getAbsoluteFile().exists()) {
            timeSheet.addAll(JAXB.unmarshal(file, TimeSheet.class));
        }
        observers.notifyObservers(new CalculateEvent());
    }

}
