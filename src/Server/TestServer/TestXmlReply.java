package Server.TestServer;

import Server.Reply.XmlReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* The following tests are for the data encapsulated in XmlReply object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestXmlReply {
    SessionToken sessionToken = new SessionToken("abc", LocalDateTime.now());
    /* Test 1: Construct a empty reply */
    @BeforeEach
    @Test
    public void TestEmptyXmlReply() {
        XmlReply xmlReply;
    }
    /* Test 2: Check if the session token has been successfully encapsulated in the reply object  */
    @Test
    public void TestXmlReplySessionToken() {
        File testFile = new File("src/xmlBillboards/starWars.xml");
        XmlReply xmlReply = new XmlReply(sessionToken, testFile);
        assertEquals(sessionToken, xmlReply.getSessionToken());
    }
    /* Test 3: Check if the file has been successfully encapsulated in the reply object  */
    @Test
    public void TestXmlReplyFile() {
        File testFile = new File("src/xmlBillboards/starWars.xml");
        XmlReply xmlReply = new XmlReply(sessionToken, testFile);
        assertEquals(testFile, xmlReply.getBillboardXml());
    }
}