package datasave;

import event.*;
import model.TimeSheet;
import model.WorkDay;

import java.io.*;
import java.util.Collection;

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
        ObjectOutputStream out = null;
        try {
            File file = new File(fileName);
            boolean created;
            if(!file.exists()) {
                created = file.createNewFile();
            } else{
                created = true;
            }
            if(created) {
                out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(timeSheet);
                out.flush();
            }else {
                throw new ErrorEvent("Can't create file!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadData(){
        File file = new File(fileName);
        if(file.getAbsoluteFile().exists()){
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(file));
                timeSheet.addAll((Collection<? extends WorkDay>) in.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
