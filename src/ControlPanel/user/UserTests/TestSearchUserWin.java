package ControlPanel.user.UserTests;
import Server.Request.EditUserRequest;
import Server.Request.SearchRequest;
import Server.SessionToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSearchUserWin {
    SessionToken sessionToken = new SessionToken("testToken", LocalDateTime.now());
    @BeforeEach @Test
    //Test 1: Construct empty EditUserRequest & SearchRequest
    public void TestSearchUserRequest(){
        SearchRequest searchRequest;
    }
    /* Tests for SearchRequest */
    @Test
    //Test 2: Check if the session token was successfully encapsulated in SearchRequest object
    public void SessionTokenTestSearchRequest(){
        SearchRequest searchRequest = new SearchRequest(sessionToken, "Boblobthelob", false);
        assertEquals(sessionToken, searchRequest.getSessionToken());
    }
    @Test
    //Test 3: Check if the Username was successfully encapsulated in SearchRequest object
    public void UsernameTestSearchRequest(){
        SearchRequest searchRequest = new SearchRequest(sessionToken, "Boblobthelob", false);
        assertEquals("Boblobthelob", searchRequest.getUserName());
    }
    @Test
    //Test 4: Check if the EditSearch boolean was successfully encapsulated in SearchRequest object
    public void EditSearchTestSearchRequest(){
        SearchRequest searchRequest = new SearchRequest(sessionToken, "Boblobthelob", false);
        assertEquals(false, searchRequest.isEditSearch());
    }
}
