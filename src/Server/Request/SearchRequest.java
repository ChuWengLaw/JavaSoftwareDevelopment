package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class SearchRequest implements Serializable {
    private SessionToken sessionToken;
    private String userName;

    public SearchRequest(SessionToken sessionToken, String userName) {
        this.sessionToken = sessionToken;
        this.userName = userName;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public String getUserName() {
        return userName;
    }
}
