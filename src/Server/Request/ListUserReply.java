package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class ListUserReply implements Serializable {
    private JTable table;

    public ListUserReply(JTable table){
        this.table = table;
    }

    public JTable getTable() {
        return table;
    }
}
