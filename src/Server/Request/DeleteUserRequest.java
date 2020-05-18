package Server.Request;

import java.io.Serializable;

public class DeleteUserRequest implements Serializable {
    private String userName;
    private String sessionToken;

    public DeleteUserRequest(String userName, String sessionToken){
        this.userName = userName;
        this.sessionToken = sessionToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
