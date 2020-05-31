package ControlPanel.user.UserTests;

import Server.Request.ChangePasswordRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in ChangePasswordRequest object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */
public class TestChangePasswordWin {
    SessionToken sessionToken = new SessionToken("testToken", LocalDateTime.now());

    //Test 1: Construct an empty ChangePasswordRequest
    @BeforeEach
    @Test
    public void TestEmptyChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest;
    }

    @Test
    //Test 2:Check if the session token was successfully encapsulated in ChangePasswordRequest
    public void SessionTokenTestChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(sessionToken,
                "Bob", "Lobster");
        assertEquals(sessionToken, changePasswordRequest.getSessionToken());
    }

    @Test
    //Test 3:Check if the Username was successfully encapsulated in ChangePasswordRequest object
    public void UsernameTestChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(sessionToken,
                "Bob", "Lobster");
        assertEquals("Bob", changePasswordRequest.getUserName());
    }

    @Test
    //Test 4:Check if the Password was successfully encapsulated in ChangePasswordRequest object
    public void PasswordTestChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(sessionToken,
                "Bob", "Lobster");
        assertEquals("Lobster", changePasswordRequest.getNewPassword());
    }
}
