package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class WeeklyScheduleReply implements Serializable {
    private String[][] Information;

    public WeeklyScheduleReply(String[][] Information){
        this.Information = Information;
    }

    public String[][] getArray() {
        return Information;
    }
}