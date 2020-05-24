package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class DeleteUserRequest implements Serializable {
    private String userName;
    private SessionToken sessionToken;

    public DeleteUserRequest(String userName, SessionToken sessionToken){
        this.userName = userName;
        this.sessionToken = sessionToken;
    }

    public String getUserName() {
        return userName;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
