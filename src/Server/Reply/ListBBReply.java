package Server.Reply;

import Server.SessionToken;

import javax.swing.*;
import java.io.Serializable;

public class ListBBReply implements Serializable {
    private SessionToken sessionToken;
    private JTable table;

    public ListBBReply(SessionToken sessionToken, JTable table){
        this.sessionToken = sessionToken;
        this.table = table;
    }

    public JTable getTable() {
        return table;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}