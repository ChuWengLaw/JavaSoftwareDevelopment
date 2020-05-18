package Server.Request;

import Server.Server;

import java.io.Serializable;

public class LoginReply implements Serializable {
    private boolean loginState;
    private String sessionToken;

    public LoginReply(boolean loginState, String sessionToken){
        this.loginState = loginState;
        this.sessionToken = sessionToken;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
