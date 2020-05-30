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
    private boolean EditSearch;

    /**
     * This is constructor.
     * @param sessionToken login user's session token
     * @param userName login user's username
     */
    public SearchRequest(SessionToken sessionToken, String userName, boolean EditSearch) {
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.EditSearch = EditSearch;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isEditSearch() {
        return EditSearch;
    }
}
