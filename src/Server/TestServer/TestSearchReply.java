package Server.TestServer;
import ControlPanel.User;
import Server.Reply.SearchReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* These tests are for the data encapsulated in SearchReply object
 * Each test will be appropriately commented to describe
 * what the test is doing and what is expected
 */

public class TestSearchReply {
    //Test 1: Create an empty SearchReply
    @BeforeEach @Test
    public void TestEmptySearchReply(){
        SearchReply searchReply;
    }
    @Test
    //Test 2: Check if RequestState was successfully encapsulated in SearchReply object
    public void RequestStateTestSearchReply(){
        User testUser = new User("Bob", true, true,
                false, false);
        SearchReply searchReply = new SearchReply(true, testUser);
        assertEquals(true, searchReply.isRequestState());
    }
    @Test
    //Test 3: Check if the testUser object was successfully encapsulated in SearchReply object
    public void UserTestSearchReply(){
        User testUser = new User("Bob", true, true,
                false, false);
        SearchReply searchReply = new SearchReply(true, testUser);
        assertEquals(testUser, searchReply.getUser());
    }
}
