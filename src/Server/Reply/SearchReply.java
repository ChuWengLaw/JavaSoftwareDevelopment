package Server.Reply;

import ControlPanel.User;
import Server.SessionToken;

import java.io.Serializable;

public class SearchReply implements Serializable {
    private SessionToken sessionToken;
    private boolean requestState;
    private User user;

    public SearchReply(SessionToken sessionToken, boolean requestState){
        this.sessionToken = sessionToken;
        this.requestState = requestState;
    }

    public SearchReply(SessionToken sessionToken, boolean requestState, User user){
        this.sessionToken = sessionToken;
        this.requestState = requestState;
        this.user = user;
    }

    public boolean isRequestState() {
        return requestState;
    }

    public User getUser() {
        return user;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
