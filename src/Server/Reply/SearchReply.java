package Server.Reply;

import ControlPanel.User;
import Server.SessionToken;

import java.io.Serializable;

/**
 * This is a reply class that handle search reply, it requires
 * an updated session token and a boolean indicates if the user exists.
 * If the user exists, a user class that contain all of the information
 * will also be passed by the search reply.
 */
public class SearchReply implements Serializable {
    // Initialize all require variables.
    private SessionToken sessionToken;
    private boolean requestState;
    private boolean EditSearch;
    private User user;

    /**
     * This is constructor if user does not exist
     * @param sessionToken an updated session token
     * @param requestState a boolean indicates if the user exists
     */
    public SearchReply(SessionToken sessionToken, boolean requestState, boolean EditSearch){
        this.sessionToken = sessionToken;
        this.requestState = requestState;
        this.EditSearch = EditSearch;
    }

    /**
     * This is constructor if user exists
     * @param sessionToken an updated session token
     * @param requestState a boolean indicates if the user exists
     * @param user a user class
     */
    public SearchReply(SessionToken sessionToken, boolean requestState, boolean EditSearch, User user){
        this.sessionToken = sessionToken;
        this.requestState = requestState;
        this.user = user;
        this.EditSearch = EditSearch;
    }

    public boolean isRequestState() {
        return requestState;
    }

    public User getUser() {
        return user;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public boolean isEditSearch() {
        return EditSearch;
    }
}
