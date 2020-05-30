package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class ListScheduleRequest implements Serializable {
    private SessionToken sessionToken;

    //build constructor and Getter functions
    public ListScheduleRequest(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
