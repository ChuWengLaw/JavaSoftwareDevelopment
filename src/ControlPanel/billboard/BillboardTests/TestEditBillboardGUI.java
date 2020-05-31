package ControlPanel.billboard.BillboardTests;

import ControlPanel.schedule.CalanderScheduleGUI;
import Server.Request.EditBBRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The following tests are for the data encapsulated in EditBBRequest object
 * <p>
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestEditBillboardGUI {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());

    /* Test 1: Construct a empty request */
    @BeforeEach
    @Test
    public void TestEmptyEditBBRequest() {
        EditBBRequest editBBRequest;
    }

    /* Test 2: Check if the input billboard name has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestBBName() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "chad", "test", true, true, CalanderScheduleGUI.IsCurrentlyScheduled("test"));
        assertEquals("test", editBBRequest.getBillboardName());
    }

    /* Test 3: Check if the input session token has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestToken() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "chad", "test", true, true, CalanderScheduleGUI.IsCurrentlyScheduled("test"));
        assertEquals(token, editBBRequest.getSessionToken());
    }

    /* Test 4: Check if the login user has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestLoginUser() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "chad", "test", true, true, CalanderScheduleGUI.IsCurrentlyScheduled("test"));
        assertEquals("chad", editBBRequest.getLoginUser());
    }

    /* Test 5: Check if the input text colour has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestTextColour() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "white", "black",
                "yellow", "blue", "green", "red");
        assertEquals("white", editBBRequest.getEditTextColour());
    }

    /* Test 6: Check if the input background colour has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestBGColour() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "white", "black",
                "yellow", "blue", "green", "red");
        assertEquals("black", editBBRequest.getEditBGColour());
    }

    /* Test 7: Check if the input message has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestMessage() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "white", "black",
                "yellow", "blue", "green", "red");
        assertEquals("yellow", editBBRequest.getEditMsg());
    }

    /* Test 8: Check if the input image has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestImage() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "white", "black",
                "yellow", "blue", "green", "red");
        assertEquals("blue", editBBRequest.getEditImg());
    }

    /* Test 9: Check if the input information has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestInfo() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "white", "black",
                "yellow", "blue", "green", "red");
        assertEquals("green", editBBRequest.getEditInfo());
    }

    /* Test 10: Check if the input information colour has been successfully encapsulated in the request object  */
    @Test
    public void TestEditBBRequestInfoColour() {
        EditBBRequest editBBRequest = new EditBBRequest(token, "white", "black",
                "yellow", "blue", "green", "red");
        assertEquals("red", editBBRequest.getEditInfoColour());
    }
}