package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class ListBBRequest implements Serializable {
    private SessionToken sessionToken;

    public ListBBRequest(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}