package Server.TestServer;

import ControlPanel.User;
import Server.Reply.LoginReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in LoginReply object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestLoginReply {
    SessionToken testSessionToken = new SessionToken("testToken", LocalDateTime.now());
    User testUser;

    //Test 1: Construct an empty LoginReply
    @BeforeEach
    @Test
    public void TestEmptyLoginReply() {
        LoginReply loginReply;
    }

    @Test
    //Test 2: Check if the LoginState was successfully encapsulated in LoginReply object
    public void LoginStateTestLoginReply() {
        testUser = new User("Bob", false, false,
                false, false);
        LoginReply loginReply = new LoginReply(true, testSessionToken, testUser);
        assertEquals(true, loginReply.isLoginState());
    }

    @Test
    //Test 3: Check if the testSessionToken was successfully encapsulated in LoginReply object
    public void SessionTokenTestLoginReply() {
        testUser = new User("Bob", false, false,
                false, false);
        LoginReply loginReply = new LoginReply(true, testSessionToken, testUser);
        assertEquals(testSessionToken, loginReply.getSessionToken());
    }

    @Test
    //Test 4: Check if the testUser object was successfully encapsulated in LoginReply object
    public void UserTestLoginReply() {
        testUser = new User("Bob", false, false,
                false, false);
        LoginReply loginReply = new LoginReply(true, testSessionToken, testUser);
        assertEquals(testUser, loginReply.getUser());
    }

}
