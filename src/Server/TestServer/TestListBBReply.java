package Server.TestServer;

import Server.Reply.ListBBReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* The following tests are for the data encapsulated in ListBBReply object
 *
 * Here we have added comments to explain what each test
 * obliges you to do during Test-Driven Development
 */

public class TestListBBReply {
    /* Test 1: Construct a empty reply */
    @BeforeEach @Test
    public void TestEmptyListBBReply() {
        ListBBReply bbInfoReply;
    }
    /* Test 2: Check if billboard table has been successfully encapsulated in the reply object  */
    @Test
    public void TestListBBReply() {
        JTable test = new JTable();
        ListBBReply bbInfoReply = new ListBBReply(test);
        assertEquals(test, bbInfoReply.getTable());
    }
}
