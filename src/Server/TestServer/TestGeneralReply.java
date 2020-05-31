package Server.TestServer;

import Server.Reply.GeneralReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in GeneralReply object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestGeneralReply {
    //Test 1: Construct an empty GeneralReply
    @BeforeEach
    @Test
    public void TestEmptyGeneralReply() {
        GeneralReply generalReply;
    }

    @Test
    //Test 2: Check if the request state was successfully encapsulated in GeneralReply object
    public void RequestStateTestGeneralReply() {
        SessionToken sessionToken = new SessionToken("abc", LocalDateTime.now());
        GeneralReply generalReply = new GeneralReply(sessionToken, true);
        assertEquals(true, generalReply.isRequestState());
    }
}
