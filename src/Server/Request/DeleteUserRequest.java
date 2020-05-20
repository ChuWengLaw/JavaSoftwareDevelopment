package Server.Request;

import java.io.Serializable;

public class DeleteUserRequest implements Serializable {
    private String userName;

    public DeleteUserRequest(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

}
