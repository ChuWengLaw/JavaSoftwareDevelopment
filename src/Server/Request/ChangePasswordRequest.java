package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a request class that handle the change password request, to execute the request,
 * it requires login user's session token, username and new password.
 */
public class ChangePasswordRequest implements Serializable {
    // Initialize all require variables.
    private SessionToken sessionToken;
    private String userName;
    private String newPassword;

    /**
     * This is the constructor.
     * @param sessionToken login user's session token.
     * @param userName login user's username.
     * @param newPassword login user's new password.
     */
    //build constructor and Getter functions
    public ChangePasswordRequest(SessionToken sessionToken, String userName, String newPassword) {
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.newPassword = newPassword;
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
