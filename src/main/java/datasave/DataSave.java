package datasave;

import event.*;
import model.TimeSheet;

import javax.xml.bind.*;
import java.io.File;

/**
 * Created by lech0816 on 25.08.2016.
 */
public class DataSave {
    private TimeSheet timeSheet;
    private Observers observers;
    private final String fileName = "data.dt";

    public DataSave(TimeSheet timeSheet, Observers observers) {
        this.timeSheet = timeSheet;
        this.observers = observers;
        observers.addListners(new Observer() {
            @Override
            public void notifyEvent(Event o) throws ErrorEvent {
                if(o.getType()== EventTypes.SAVE){
                    saveData();
                } else if(o.getType()==EventTypes.LOAD){
                    loadData();
                }
            }
        });
    }

    private void saveData() throws ErrorEvent {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance( TimeSheet.class );
            Marshaller jaxbMarshaller;
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
            jaxbMarshaller.marshal( timeSheet, new File( "data.xml" ) );
        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    private void loadData(){
        File file = new File("data.xml");
        if(file.getAbsoluteFile().exists()) {
            timeSheet.addAll(JAXB.unmarshal(file, TimeSheet.class));
        }
    }

}
