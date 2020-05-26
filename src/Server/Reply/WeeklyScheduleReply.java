package Server.Reply;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class WeeklyScheduleReply implements Serializable {
    private ArrayList<String[]> Array;

    public WeeklyScheduleReply(ArrayList<String[]> Array){
        this.Array = Array;
    }

    public ArrayList<String[]> getArray() {
        return Array;
    }
}