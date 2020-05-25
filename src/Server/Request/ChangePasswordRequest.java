package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class ChangePasswordRequest implements Serializable {
    private SessionToken sessionToken;
    private String userName;
    private String newPassword;

    public ChangePasswordRequest(SessionToken sessionToken, String userName, String newPassword){
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.newPassword =newPassword;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
