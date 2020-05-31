package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class WeeklyScheduleRequest implements Serializable {
    private SessionToken sessionToken;
    //build constructor and Getter functions
    public WeeklyScheduleRequest(SessionToken sessionToken){
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}