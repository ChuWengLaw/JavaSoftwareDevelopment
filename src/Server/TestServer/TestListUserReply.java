package Server.TestServer;
import Server.Reply.ListUserReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

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
        ListUserReply listUserReply = new ListUserReply(testTable, true);
        assertEquals(testTable, listUserReply.getTable());
    }
    @Test
    //Test 2: Check if the ListUserState was successfully encapsulated in ListUserReply object
    public void ListUserStateTestListUserReply(){
        JTable testTable = new JTable();
        ListUserReply listUserReply = new ListUserReply(testTable, true);
        assertEquals(true, listUserReply.isListUserState());
    }
}
