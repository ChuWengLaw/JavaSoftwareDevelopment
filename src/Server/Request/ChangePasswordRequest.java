package Server.Request;

import java.io.Serializable;

public class ChangePasswordRequest implements Serializable {
    private String userName;
    private String newPassword;

    public ChangePasswordRequest(String userName, String newPassword){
        this.userName = userName;
        this.newPassword =newPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
