package ControlPanel.billboard.BillboardTests;

import ControlPanel.schedule.CalanderScheduleGUI;
import Server.Request.DeleteBBRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The following tests are for the data encapsulated in DeleteBillboardRequest object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestDeleteBillboardGUI {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());
    /* Test 1: Construct a empty request */
    @BeforeEach @Test
    public void TestEmptyDeleteBBRequest() {
        DeleteBBRequest deleteBBRequest;
    }
    /* Test 2: Check if the input billboard name has been successfully encapsulated in the request object  */
    @Test
    public void TestDeleteBBRequestBBName() {
        DeleteBBRequest deleteBBRequest = new DeleteBBRequest(token, "test", "chad", true, true, CalanderScheduleGUI.IsCurrentlyScheduled("test"));
        assertEquals("test", deleteBBRequest.getBillboardName());
    }
    /* Test 3: Check if the username has been successfully encapsulated in the request object  */
    @Test
    public void TestDeleteBBRequestUserName() {
        DeleteBBRequest deleteBBRequest = new DeleteBBRequest(token, "test", "chad", true, true,CalanderScheduleGUI.IsCurrentlyScheduled("test"));
        assertEquals("chad", deleteBBRequest.getBillboardName());
    }
    /* Test 4: Check if the input session token has been successfully encapsulated in the request object  */
    @Test
    public void TestDeleteBBRequestToken() {
        DeleteBBRequest deleteBBRequest = new DeleteBBRequest(token, "test", "chad", true, true,CalanderScheduleGUI.IsCurrentlyScheduled("test"));
        assertEquals(token, deleteBBRequest.getSessionToken());
    }
}
