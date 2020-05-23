package Server.Request;

import java.io.Serializable;

public class ListBBRequest implements Serializable {
    private String sessionToken;
    public ListBBRequest(String sessionToken){
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}