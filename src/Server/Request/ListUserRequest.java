package Server.Request;

import java.io.Serializable;

public class ListUserRequest implements Serializable {
    private String sessionToken;
    public ListUserRequest(String sessionToken){
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
