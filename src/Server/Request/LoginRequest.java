package Server.Request;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a request class that handle the login request, to execute the request,
 * it requires login user's username and password.
 */
public class LoginRequest implements Serializable {
    private String userName;
    private String password;

    /**
     * This is constructor.
     *
     * @param userName login user's username
     * @param password login user's password
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //build constructor and Getter functions
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
