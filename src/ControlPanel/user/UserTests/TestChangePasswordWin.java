package ControlPanel.user.UserTests;
import Server.Request.ChangePasswordRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/* These tests are for the data encapsulated in CreateUserRequest object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */
public class TestChangePasswordWin {
    SessionToken sessionToken;
    //Test 1: Construct an empty request
    @BeforeEach @Test
    public void TestEmptyChangePasswordRequest(){
        ChangePasswordRequest changePasswordRequest;
    }
    @Test
    //Test 2:Check if the session token was successfully encapsulated in ChangePasswordRequest
    public void SessionTokenTestChangePasswordRequest(){
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(sessionToken,
                "Bob", "Lobster");
        assertEquals(sessionToken, changePasswordRequest.getSessionToken());
    }
    @Test
    //Test 3:Check if the Username was successfully encapsulated in ChangePasswordRequest
    public void UsernameTestChangePasswordRequest(){
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(sessionToken,
                "Bob", "Lobster");
        assertEquals("Bob", changePasswordRequest.getUserName());
    }
    @Test
    //Test 4:Check if the Password was successfully encapsulated in ChangePasswordRequest
    public void PasswordTestChangePasswordRequest(){
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(sessionToken,
                "Bob", "Lobster");
        assertEquals("Lobster", changePasswordRequest.getNewPassword());
    }
}
