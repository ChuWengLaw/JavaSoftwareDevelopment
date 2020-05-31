package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a request class that handle the logout request, to execute the request,
 * it requires login user's session token.
 */
public class LogoutRequest implements Serializable {
    private SessionToken sessionToken;

    /**
     * This is constructor.
     *
     * @param sessionToken login user's session token
     */
    //build constructor and Getter functions
    public LogoutRequest(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
