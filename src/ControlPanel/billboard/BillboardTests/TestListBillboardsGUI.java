package ControlPanel.billboard.BillboardTests;

import Server.Request.ListBBRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The following tests are for the data encapsulated in ListBBRequest object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestListBillboardsGUI {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());
    /* Test 1: Construct a empty request */
    @BeforeEach @Test
    public void TestEmptyListBBRequest() {
        ListBBRequest listBBRequest;
    }
    /* Test 2: Check if the input session token has been successfully encapsulated in the request object  */
    @Test
    public void TestListBBRequestToken() {
        ListBBRequest listBBRequest = new ListBBRequest(token);
        assertEquals(token, listBBRequest.getSessionToken());
    }
}
