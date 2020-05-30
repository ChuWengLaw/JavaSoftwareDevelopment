package Server.Reply;

import Server.SessionToken;

import javax.swing.*;
import java.io.Serializable;

public class ListUserReply implements Serializable {
    private SessionToken sessionToken;
    private JTable table;
    private boolean listUserState;

    //build constructor and Getter functions
    public ListUserReply(SessionToken sessionToken, JTable table, boolean listUserState){
        this.sessionToken = sessionToken;
        this.table = table;
        this.listUserState = listUserState;
    }

    public JTable getTable() {
        return table;
    }

    public boolean isListUserState() {
        return listUserState;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
