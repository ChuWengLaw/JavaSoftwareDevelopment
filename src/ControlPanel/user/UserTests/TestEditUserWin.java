package ControlPanel.user.UserTests;
import Server.Request.EditUserRequest;
import Server.Request.SearchRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
/* These tests are for the data encapsulated in EditUserRequest/SearchRequest object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */
public class TestEditUserWin {
    SessionToken sessionToken = new SessionToken("testToken", LocalDateTime.now());
    @BeforeEach @Test
    //Test 1: Construct empty EditUserRequest & SearchRequest
    public void TestEmptyEditUserSearchRequest(){
        EditUserRequest editUserRequest;
        SearchRequest searchRequest;
    }
    /* Tests for SearchRequest */
    @Test
    //Test 2: Check if the session token was successfully encapsulated in SearchRequest object
    public void SessionTokenTestSearchRequest(){
        SearchRequest searchRequest = new SearchRequest(sessionToken, "Lobster", true);
        assertEquals(sessionToken, searchRequest.getSessionToken());
    }
    @Test
    //Test 3: Check if the Username was successfully encapsulated in SearchRequest object
    public void UsernameTestSearchRequest(){
        SearchRequest searchRequest = new SearchRequest(sessionToken, "Lobster", true);
        assertEquals("Lobster", searchRequest.getUserName());
    }
    /* Tests for EditUserRequest Part 1 if a password is present*/
    @Test
    //Test 4: Check if the session token was successfully encapsulated in EditUserRequest object
    public void SessionTokenTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals(sessionToken, editUserRequest.getSessionToken());
    }
    @Test
    //Test 5: Check if the Username was successfully encapsulated in EditUserRequest object
    public void UsernameTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals("Lobster", editUserRequest.getUserName());
    }
    @Test
    //Test 6: Check if the Password was successfully encapsulated in EditUserRequest object
    public void PasswordTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals("Delicious", editUserRequest.getUserPassword());
    }
    @Test
    //Test 7: Check if the CreateBillboardsPermission was successfully encapsulated in EditUserRequest object
    public void CreateBillboardsPermissionTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals(true, editUserRequest.isCreateBillboardsPermission());
    }
    @Test
    //Test 8: Check if the EditAllBillboardPermission was successfully encapsulated in EditUserRequest object
    public void EditAllBillboardPermissionTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals(true, editUserRequest.isEditAllBillboardPermission());
    }
    @Test
    //Test 9: Check if the ScheduleBillboardsPermission was successfully encapsulated in EditUserRequest object
    public void ScheduleBillboardsPermissionTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals(true, editUserRequest.isScheduleBillboardsPermission());
    }
    @Test
    //Test 10: Check if the EditUsersPermission was successfully encapsulated in EditUserRequest object
    public void EditUsersPermissionTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals(false, editUserRequest.isEditUsersPermission());
    }
    @Test
    //Test 11: Check if the HavePassword was successfully encapsulated in EditUserRequest object
    public void HavePasswordTestEditUserRequest(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Lobster", "Delicious",
                true, true,
                true , false, true);
        assertEquals(true, editUserRequest.isHavePassword());
    }
    /* Tests for EditUserRequest Part 2 if a password is not present*/
    @Test
    //Test 12: Check if the Username was successfully encapsulated in EditUserRequest object
    public void UsernameTestEditUserRequest2(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken,"Bobster", true,
                true, false, false, false);
        assertEquals("Bobster", editUserRequest.getUserName());
    }
    @Test
    //Test 13: Check if the CreateBillboardsPermission was successfully encapsulated in EditUserRequest object
    public void CreateBillboardsPermissionTestEditUserRequest2(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Bobster", true,
                true, false, false, false);
        assertEquals(true, editUserRequest.isCreateBillboardsPermission());
    }
    @Test
    //Test 14: Check if the EditAllBillboardPermission was successfully encapsulated in EditUserRequest object
    public void EditAllBillboardPermissionTestEditUserRequest2(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Bobster", true,
                true, false, false, false);
        assertEquals(true, editUserRequest.isEditAllBillboardPermission());
    }
    @Test
    //Test 15: Check if the ScheduleBillboardsPermission was successfully encapsulated in EditUserRequest object
    public void ScheduleBillboardsPermissionTestEditUserRequest2(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Bobster", true,
                true, false, false, false);
        assertEquals(false, editUserRequest.isScheduleBillboardsPermission());
    }
    @Test
    //Test 16: Check if the EditUsersPermission was successfully encapsulated in EditUserRequest object
    public void EditUsersPermissionTestEditUserRequest2(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Bobster", true,
                true, false, false, false);
        assertEquals(false, editUserRequest.isScheduleBillboardsPermission());
    }
    @Test
    //Test 17: Check if the HavePassword was successfully encapsulated in EditUserRequest object
    public void HavePasswordPermissionTestEditUserRequest2(){
        EditUserRequest editUserRequest = new EditUserRequest(sessionToken, "Bobster", true,
                true, false, false, false);
        assertEquals(false, editUserRequest.isHavePassword());
    }
}

