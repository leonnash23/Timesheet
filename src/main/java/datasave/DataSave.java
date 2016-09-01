package datasave;

import event.Observers;
import event.Observer;
import event.EventTypes;
import event.Event;
import event.ErrorEvent;
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

    public DataSave(final TimeSheet timeSheet, final Observers observers) {
        this.timeSheet = timeSheet;
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

    private void loadData() {
        File file = new File(FILE_NAME);
        if (file.getAbsoluteFile().exists()) {
            timeSheet.addAll(JAXB.unmarshal(file, TimeSheet.class));
        }
    }

}
