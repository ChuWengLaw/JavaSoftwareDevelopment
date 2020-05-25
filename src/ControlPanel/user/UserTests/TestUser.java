package ControlPanel.user.UserTests;
import ControlPanel.User;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data stored in User object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestUser {
    SessionToken sessionToken = new SessionToken("testToken", LocalDateTime.now());
    User testUser;
    //Test 1: Construct an empty User
    @BeforeEach @Test
    public void TestEmptyUser(){
        testUser = new User();
    }
    @Test
    //Test 2: Check if the Username was stored successfully in the User object
    public void UsernameTestUser(){
        testUser = new User("Boblob", true, false,
                true, false);
        assertEquals("Boblob", testUser.getUserName());
    }
    @Test
    //Test 3: Check if the CreateBillboardsPermission was stored successfully in the User object
    public void CreateBillboardsPermissionTestUser(){
        testUser = new User("Boblob", true, false,
                true, false);
        assertEquals(true, testUser.getCreateBillboardsPermission());
    }
    @Test
    //Test 4: Check if the EditAllBillboardPermission was stored successfully in the User object
    public void EditAllBillboardPermissionTestUser(){
        testUser = new User("Boblob", true, false,
                true, false);
        assertEquals(false, testUser.getEditAllBillboardPermission());
    }
    @Test
    //Test 5: Check if the ScheduleBillboardsPermission was stored successfully in the User object
    public void ScheduleBillboardsPermissionTestUser(){
        testUser = new User("Boblob", true, false,
                true, false);
        assertEquals(true, testUser.getScheduleBillboardsPermission());
    }
    @Test
    //Test 6: Check if the EditUsersPermission was stored successfully in the User object
    public void EditUsersPermissionTestUser(){
        testUser = new User("Boblob", true, false,
                true, false);
        assertEquals(false, testUser.getEditUsersPermission());
    }
    @Test
    //Test 7: Check if the SessionToken object was stored successfully in the User object
    public void SessionTokenPermissionTestUser(){
        testUser = new User("Boblob", true, false,
                true, false);
        sessionToken = null;
        testUser.setSessionToken(sessionToken);
        assertEquals(null, testUser.getSessionToken());
    }
}
