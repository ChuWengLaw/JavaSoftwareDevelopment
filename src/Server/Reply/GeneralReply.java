package Server.Reply;

import Server.SessionToken;

import java.io.Serializable;

public class GeneralReply implements Serializable{
    private SessionToken sessionToken;
    private boolean requestState;

    public GeneralReply(SessionToken sessionToken, boolean requestState){
        this.sessionToken = sessionToken;
        this.requestState = requestState;
    }

    public boolean isRequestState() {
        return requestState;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
