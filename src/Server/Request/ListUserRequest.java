package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class ListUserRequest implements Serializable {
    private SessionToken sessionToken;
    public ListUserRequest(SessionToken sessionToken){
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
