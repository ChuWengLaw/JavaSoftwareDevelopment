package ControlPanel.user.UserTests;

import Server.Request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in LoginWin object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */
public class TestLoginWin {
    //Test 1: Construct an empty LoginRequest
    @BeforeEach
    @Test
    public void TestEmptyLoginRequest() {
        LoginRequest loginRequest;
    }

    //Test 2: Check if the Username was successfully encapsulated in LoginRequest object
    @Test
    public void UsernameTestLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("Bob", "Lob");
        assertEquals("Bob", loginRequest.getUserName());
    }

    //Test 3: Check if the Password was successfully encapsulated in LoginRequest object
    @Test
    public void PasswordTestLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("Bob", "Lob");
        assertEquals("Lob", loginRequest.getPassword());
    }
}
