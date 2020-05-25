package ControlPanel.billboard.BillboardTests;

import Server.Request.CreateBBRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The following tests are for the data encapsulated in CreateBillboardRequest object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestCreateBillboardGUI {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());
    /* Test 1: Construct a empty request */
    @BeforeEach @Test
    public void TestEmptyCreateBBRequest() {
        CreateBBRequest createBBRequest;
    }
    /* Test 2: Check if the input billboard name has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestBBName() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token, "chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals("chad", createBBRequest.getBillboardName());
    }
    /* Test 3: Check if username has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestUserName() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token, "chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals("john", createBBRequest.getUserName());
    }
    /* Test 4: Check if the input billboard text colour has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestBBTextColour() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token,"chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals("blue", createBBRequest.getTextColour());
    }
    /* Test 5: Check if the input billboard background colour has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestBackgroundColour() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token,"chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals("black", createBBRequest.getBackgroundColour());
    }
    /* Test 6: Check if the input billboard message has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestMessage() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token,"chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");

        assertEquals("hello world", createBBRequest.getMessage());
    }
    /* Test 7: Check if the input billboard image has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestImage() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token,"chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals("unknown.xml", createBBRequest.getImage());
    }
    /* Test 8: Check if the input billboard information has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestInformation() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token,"chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals("one plus one is zero", createBBRequest.getInformation());
    }
    /* Test 9: Check if the input session token has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestToken() {
        CreateBBRequest createBBRequest = new CreateBBRequest(token,"chad", "john","blue", "black",
                "hello world", "unknown.xml", "one plus one is zero", "white");
        assertEquals(token, createBBRequest.getSessionToken());
    }
}
