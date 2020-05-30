package Server.Request;

import Server.SessionToken;

import java.io.Serializable;
/**
 * This is the request class that handles the delete user request
 * sent from the delete user GUI. It takes the entered user and
 * the login user's session token as input
 *
 * @author "Kenji" Foo Shiang Xun
 */
public class DeleteUserRequest implements Serializable {
    private String userName;
    private SessionToken sessionToken;

    /**
     *This is the constructor that stores the data sent from client
     * @param userName inputted username
     * @param sessionToken login user's session token
     */
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
