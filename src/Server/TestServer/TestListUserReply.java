package Server.TestServer;
import Server.Reply.ListUserReply;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in ListUserReply object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestListUserReply {
    //Test 1: Construct an empty ListUserReply
    @BeforeEach @Test
    public void TestEmptyListUserReply(){
        ListUserReply listUserReply;
    }
    @Test
    //Test 2: Check if the user table was successfully encapsulated in ListUserReply object
    public void UserTableTestListUserReply(){
        JTable testTable = new JTable();
        SessionToken sessionToken = new SessionToken("abc", LocalDateTime.now());
        ListUserReply listUserReply = new ListUserReply(sessionToken, testTable, true);
        assertEquals(testTable, listUserReply.getTable());
    }
    @Test
    //Test 2: Check if the ListUserState was successfully encapsulated in ListUserReply object
    public void ListUserStateTestListUserReply(){
        JTable testTable = new JTable();
        SessionToken sessionToken = new SessionToken("abc", LocalDateTime.now());
        ListUserReply listUserReply = new ListUserReply(sessionToken,testTable, true);
        assertEquals(true, listUserReply.isListUserState());
    }
}
