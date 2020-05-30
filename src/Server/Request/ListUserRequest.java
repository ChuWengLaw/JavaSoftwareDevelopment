package Server.Request;

import Server.SessionToken;

import java.io.Serializable;
/**
 * This is the request class that handles the list user request
 * sent from the delete user GUI. It only takes the login user's
 * session token as input.
 *
 * @author "Kenji" Foo Shiang Xun
 */
public class ListUserRequest implements Serializable {
    private SessionToken sessionToken;

    /**
     * This is the constructor that stores the data sent from the client
     * build constructor and Getter functions
     * @param sessionToken
     */
    public ListUserRequest(SessionToken sessionToken){
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
