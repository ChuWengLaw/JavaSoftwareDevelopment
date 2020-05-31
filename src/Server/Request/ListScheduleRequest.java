package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class ListScheduleRequest implements Serializable {
    private SessionToken sessionToken;
    /**
     * This is the request class that handles the list schedule request
     * @param sessionToken
     *
     * @author Callum Longman
     */
    //build constructor and Getter functions
    public ListScheduleRequest(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
