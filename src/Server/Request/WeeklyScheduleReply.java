package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class WeeklyScheduleReply implements Serializable {
    private JTable table;

    public WeeklyScheduleReply(JTable table){
        this.table = table;
    }

    public JTable getTable() {
        return table;
    }
}