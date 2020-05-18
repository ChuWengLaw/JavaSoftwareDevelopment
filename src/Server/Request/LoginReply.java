package Server.Request;

import ControlPanel.User;
import java.io.Serializable;

public class LoginReply implements Serializable {
    private boolean loginState;
    private String sessionToken;
    private User user;

    public LoginReply(boolean loginState, String sessionToken, User user){
        this.loginState = loginState;
        this.sessionToken = sessionToken;
        this.user = user;
    }

    public LoginReply(boolean loginState, String sessionToken){
        this.loginState = loginState;
        this.sessionToken = sessionToken;
        this.user = user;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public User getUser() {
        return user;
    }
}
