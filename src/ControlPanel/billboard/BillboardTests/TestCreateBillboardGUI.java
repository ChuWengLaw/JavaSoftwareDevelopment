package ControlPanel.billboard.BillboardTests;

import Server.Request.CreateBBRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* The following tests are for the data encapsulated in CreateBillboardRequest object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestCreateBillboardGUI {
    /* Test 1: Construct a empty request */
    @BeforeEach @Test
    public void TestEmptyCreateBBRequest() {
        CreateBBRequest createBBRequest;
    }
    /* Test 2: Check if the input billboard name has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestBBName() {
        CreateBBRequest createBBRequest = new CreateBBRequest("chad", "blue", "black",
                "hello world", "unknown.xml", "one plus one is zero","");
        assertEquals("chad", createBBRequest.getBillboardName());
    }
    /* Test 3: Check if the input billboard text colour has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestBBTextColour() {
        CreateBBRequest createBBRequest = new CreateBBRequest("chad", "blue", "black",
                "hello world", "unknown.xml", "one plus one is zero","");
        assertEquals("blue", createBBRequest.getTextColour());
    }
    /* Test 4: Check if the input billboard background colour has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestBackgroundColour() {
        CreateBBRequest createBBRequest = new CreateBBRequest("chad", "blue", "black",
                "hello world", "unknown.xml", "one plus one is zero","");
        assertEquals("black", createBBRequest.getBackgroundColour());
    }
    /* Test 5: Check if the input billboard message has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestMessage() {
        CreateBBRequest createBBRequest = new CreateBBRequest("chad", "blue", "black",
                "hello world", "unknown.xml", "one plus one is zero","");
        assertEquals("hello world", createBBRequest.getMessage());
    }
    /* Test 3: Check if the input billboard image has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestImage() {
        CreateBBRequest createBBRequest = new CreateBBRequest("chad", "blue", "black",
                "hello world", "unknown.xml", "one plus one is zero","");
        assertEquals("unknown.xml", createBBRequest.getImage());
    }
    /* Test 3: Check if the input billboard information has been successfully encapsulated in the request object  */
    @Test
    public void TestCreateBBRequestInformation() {
        CreateBBRequest createBBRequest = new CreateBBRequest("chad", "blue", "black",
                "hello world", "unknown.xml", "one plus one is zero","");
        assertEquals("one plus one is zero", createBBRequest.getInformation());
    }
}
