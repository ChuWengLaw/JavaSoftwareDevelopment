package ControlPanel.billboard.BillboardTests;

import Server.Request.DeleteBBRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* The following tests are for the data encapsulated in DeleteBillboardRequest object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestDeleteBillboardGUI {
    /* Test 1: Construct a empty request */
    @BeforeEach @Test
    public void TestEmptyDeleteBBRequest() {
        DeleteBBRequest deleteBBRequest;
    }
    /* Test 2: Check if the input billboard name has been successfully encapsulated in the request object  */
    @Test
    public void TestDeleteBBRequestBBName() {
        DeleteBBRequest deleteBBRequest = new DeleteBBRequest("chad");
        assertEquals("chad", deleteBBRequest.getBillboardName());
    }
}
