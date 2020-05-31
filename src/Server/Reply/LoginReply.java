package Server.Reply;

import ControlPanel.User;
import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a reply class that handle the login reply, it requires
 * a boolean indicates login state. If login is successful, login user's information
 * and a session token will also be included in the reply.
 */
public class LoginReply implements Serializable {
    // Initialize all require variables.
    private boolean loginState;
    private SessionToken sessionToken;
    private User user;

    /**
     * This is constructor for successful login.
     *
     * @param loginState   a boolean indicates login state
     * @param sessionToken a session token that will be assigned to the login user
     * @param user         login user's information
     */
    public LoginReply(boolean loginState, SessionToken sessionToken, User user) {
        this.loginState = loginState;
        this.sessionToken = sessionToken;
        this.user = user;
    }

    /**
     * This is constructor for unsuccessful login.
     *
     * @param loginState a boolean indicates login state
     */
    public LoginReply(boolean loginState) {
        this.loginState = loginState;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public User getUser() {
        return user;
    }
}
