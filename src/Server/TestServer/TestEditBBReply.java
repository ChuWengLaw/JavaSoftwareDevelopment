package Server.TestServer;

import Server.Reply.EditBBReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The following tests are for the data encapsulated in EditBBReply object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestEditBBReply {
    // Session token for testing
    SessionToken token = new SessionToken("token", LocalDateTime.now());
    /* Test 1: Construct a empty reply */
    @BeforeEach @Test
    public void TestEmptyEditBBReply() {
        EditBBReply bbInfoReply;
    }
    /* Test 2: Check if the input text colour has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplyTextColour() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals("white", editBBReply.getEditTextColour());
    }
    /* Test 3: Check if the input background colour has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplyBGColour() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals("red", editBBReply.getEditBGColour());
    }
    /* Test 4: Check if the input message has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplyMessage() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals("black", editBBReply.getEditMsg());
    }
    /* Test 5: Check if the input image has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplyImage() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals("blue", editBBReply.getEditImg());
    }
    /* Test 6: Check if the input information has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplyInformation() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals("orange", editBBReply.getEditInfo());
    }
    /* Test 7: Check if the input information colour has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplyInformationColour() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals("green", editBBReply.getEditInfoColour());
    }
    /* Test 7: Check if the session token has been successfully encapsulated in the reply object  */
    @Test
    public void TestEditBBReplySessionToken() {
        EditBBReply editBBReply = new EditBBReply(token,"white", "red",
                "black", "blue", "orange", "green");
        assertEquals(token, editBBReply.getSessionToken());
    }
}