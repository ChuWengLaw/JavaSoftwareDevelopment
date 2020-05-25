package Server.TestServer;
import ControlPanel.User;
import Server.Request.LogoutReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in LogoutReply object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestLogoutReply {
    //Test 1: Construct an empty LogoutReply
    @BeforeEach @Test
    public void TestEmptyLogoutReply(){
        LogoutReply logoutReply;
    }
    @Test
    //Test 2: Check if the expired boolean was successfully encapsulated in LogoutReply object
    public void ExpiredTestLogoutReply(){
        LogoutReply logoutReply = new LogoutReply(true);
        assertEquals(true, logoutReply.isExpired());
    }
}
