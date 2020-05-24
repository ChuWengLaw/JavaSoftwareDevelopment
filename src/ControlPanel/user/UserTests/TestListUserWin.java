package ControlPanel.user.UserTests;
import Server.Request.ListUserRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/* These tests are for the data encapsulated in CreateUserRequest object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestListUserWin {
    SessionToken sessionToken;
    //Test 1: Create an empty ListUserRequest
    @BeforeEach @Test
    public void TestEmptyListUserRequest(){
        ListUserRequest listUserRequest;
    }
    //Test 2: Check if the session token was successfully encapsulated in ListUserReqest object
    @Test
    public void SessionTokenListUserRequest(){
        ListUserRequest listUserRequest = new ListUserRequest(sessionToken);
        assertEquals(sessionToken, listUserRequest.getSessionToken());
    }
}
