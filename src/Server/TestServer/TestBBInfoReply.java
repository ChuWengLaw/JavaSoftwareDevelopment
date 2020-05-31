package Server.TestServer;

import Server.Reply.BBInfoReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* The following tests are for the data encapsulated in BBInfoReply object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestBBInfoReply {
    SessionToken token = new SessionToken("abc", LocalDateTime.now());

    /* Test 1: Construct a empty reply */
    @BeforeEach
    @Test
    public void TestEmptyBBInfoReply() {
        BBInfoReply bbInfoReply;
    }

    /* Test 2: Check if the input billboard session token has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplySessionToken() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals(token, bbInfoReply.getSessionToken());
    }

    /* Test 2: Check if the input billboard text colour has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplyBBTextColour() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals("white", bbInfoReply.getEditTextColour());
    }

    /* Test 2: Check if the input billboard background colour has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplyBackgroundColour() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals("red", bbInfoReply.getEditBGColour());
    }

    /* Test 2: Check if the input billboard message has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplyMessage() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals("black", bbInfoReply.getEditMsg());
    }

    /* Test 2: Check if the input billboard image has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplyImage() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals("blue", bbInfoReply.getEditImg());
    }

    /* Test 2: Check if the input billboard info has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplyBBInfo() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals("orange", bbInfoReply.getEditInfo());
    }

    /* Test 2: Check if the input billboard info colour has been successfully encapsulated in the reply object  */
    @Test
    public void TestBBInfoReplyBBInfoColour() {

        BBInfoReply bbInfoReply = new BBInfoReply(token, "white", "red",
                "black", "blue", "orange", "green");
        assertEquals("green", bbInfoReply.getEditInfoColour());
    }
}
