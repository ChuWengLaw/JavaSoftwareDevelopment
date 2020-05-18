package Server.Request;

import java.io.Serializable;

public class SearchRequest implements Serializable {
    private String userName;

    public SearchRequest(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
