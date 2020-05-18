package Server.Request;

import java.io.Serializable;
import java.util.Vector;

public class ListUserReply implements Serializable {
    private Vector data;
    private Vector columnheader;

    public ListUserReply(Vector data, Vector columnheader){
        this.data = data;
        this.columnheader = columnheader;
    }

    public Vector getData() {
        return data;
    }

    public Vector getColumnheader() {
        return columnheader;
    }
}
