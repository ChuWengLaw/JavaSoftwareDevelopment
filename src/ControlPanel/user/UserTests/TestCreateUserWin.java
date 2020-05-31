package ControlPanel.user.UserTests;

import Server.Request.CreateUserRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in CreateUserRequest object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */
public class TestCreateUserWin {
    SessionToken sessionToken = new SessionToken("testToken", LocalDateTime.now());

    //Test 1: Construct an empty CreateUserRequest
    @BeforeEach
    @Test
    public void TestEmptyCreateUserRequest() {
        CreateUserRequest createUserRequest;
    }

    //Test 2: Check if the session token was successfully encapsulated in CreateUserRequest object
    @Test
    public void SessionTokenTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals(sessionToken, createUserRequest.getSessionToken());
    }

    //Test 3: Check if the Username was successfully encapsulated in CreateUserRequest object
    @Test
    public void UsernameTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals("Bob", createUserRequest.getUserName());
    }

    //Test 4: Check if the Password was successfully encapsulated in CreateUserRequest object
    @Test
    public void PasswordTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals("Lob", createUserRequest.getUserPassword());
    }

    //Test 5: Check if the CreateBillboardsPermission was successfully encapsulated in CreateUserRequest object
    @Test
    public void CreateBillboardsPermissionTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals(true, createUserRequest.isCreateBillboardsPermission());
    }

    //Test 6: Check if the EditAllBillboardPermission was successfully encapsulated in CreateUserRequest object
    @Test
    public void EditAllBillboardPermissionTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals(true, createUserRequest.isEditAllBillboardPermission());
    }

    //Test 7: Check if the ScheduleBillboardsPermission was successfully encapsulated in CreateUserRequest object
    @Test
    public void ScheduleBillboardsPermissionTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals(false, createUserRequest.isScheduleBillboardsPermission());
    }

    //Test 8: Check if the EditUsersPermission was successfully encapsulated in CreateUserRequest object
    @Test
    public void EditUsersPermissionTestCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest(sessionToken, "Bob", "Lob",
                true, true, false, false);
        assertEquals(false, createUserRequest.isEditUsersPermission());
    }

}
