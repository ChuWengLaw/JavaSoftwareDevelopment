package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a request class that handle search request, to execute the request,
 * it requires login user's session token and username.
 */
public class SearchRequest implements Serializable {
    private SessionToken sessionToken;
    private String userName;

    /**
     * This is constructor.
     * @param sessionToken login user's session token
     * @param userName login user's username
     */
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
