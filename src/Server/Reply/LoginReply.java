package Server.Reply;

import ControlPanel.User;
import Server.SessionToken;

import java.io.Serializable;

public class LoginReply implements Serializable {
    private boolean loginState;
    private SessionToken sessionToken;
    private User user;

    public LoginReply(boolean loginState, SessionToken sessionToken, User user) {
        this.loginState = loginState;
        this.sessionToken = sessionToken;
        this.user = user;
    }

    public LoginReply(boolean loginState, SessionToken sessionToken) {
        this.loginState = loginState;
        this.sessionToken = sessionToken;
        this.user = user;
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
