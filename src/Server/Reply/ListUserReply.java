package Server.Reply;

import Server.SessionToken;

import javax.swing.*;
import java.io.Serializable;

/**
 * This is the reply class that handle the list user reply.
 * It takes the sessionToken, JTable, and listUserState as an input
 * If the request was successful, it will return the sessionToken,
 * table and the user state
 *
 * @author "Kenji" Foo Shiang Xun
 */
public class ListUserReply implements Serializable {
    private SessionToken sessionToken;
    private JTable table;
    private boolean listUserState;


    /**
     * This is the constructor for listing the users in a table.
     * Build constructor and Getter functions
     * @param sessionToken
     * @param table
     * @param listUserState
    */
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