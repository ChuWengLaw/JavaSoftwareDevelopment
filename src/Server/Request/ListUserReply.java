package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class ListUserReply implements Serializable {
    private JTable table;
    private boolean listUserState;

    public ListUserReply(JTable table, boolean listUserState){
        this.table = table;
        this.listUserState = listUserState;
    }

    public JTable getTable() {
        return table;
    }

    public boolean isListUserState() {
        return listUserState;
    }
}
