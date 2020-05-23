package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class LogoutRequest implements Serializable {
    private SessionToken sessionToken;

    public LogoutRequest(SessionToken sessionToken){
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
