package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class ListUserReply implements Serializable {
    private JTable table;
    private boolean validSession;

    public ListUserReply(JTable table, boolean validSession){
        this.table = table;
        this.validSession = validSession;
    }

    public JTable getTable() {
        return table;
    }

    public boolean isValidSession() {
        return validSession;
    }
}
