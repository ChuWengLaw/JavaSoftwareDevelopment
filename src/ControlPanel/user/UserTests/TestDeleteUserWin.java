package ControlPanel.user.UserTests;
import Server.Request.DeleteUserRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/* These tests are for the data encapsulated in CreateUserRequest object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */
public class TestDeleteUserWin {
    SessionToken sessionToken;
    //Test 1: Construct an empty DeleteUserRequest
    @BeforeEach @Test
    public void TestEmptyDeleteUserRequest(){
        DeleteUserRequest deleteUserRequest;
    }
    //Test 2: Check if the Username was successfully encapsulated in DeleteUserRequest object
    @Test
    public void UsernameTestDeleteUserRequest(){
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest("Boblobthelob", sessionToken);
        assertEquals("Boblobthelob", deleteUserRequest.getUserName());
    }
    //Test 3: Check if the session token was successfully encapsulated in DeleteUserRequest object
    @Test
    public void SessionTokenDeleteUserRequest(){
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest("Boblobthelob", sessionToken);
        assertEquals(sessionToken, deleteUserRequest.getSessionToken());
    }

}
