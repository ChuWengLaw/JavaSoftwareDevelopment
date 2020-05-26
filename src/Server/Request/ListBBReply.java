package Server.Request;

import javax.swing.*;
import java.io.Serializable;

public class ListBBReply implements Serializable {
    private JTable table;

    public ListBBReply(JTable table) {
        this.table = table;
    }

    public JTable getTable() {
        return table;
    }
}