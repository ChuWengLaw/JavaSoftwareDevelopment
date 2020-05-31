package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class DeleteScheduleRequest implements Serializable {
    private String ScheduledName;
    private String ScheduledTime;
    private SessionToken sessionToken;

    //build constructor and Getter functions
    public DeleteScheduleRequest(String ScheduledName, String ScheduledTime, SessionToken sessionToken) {
        this.ScheduledName = ScheduledName;
        this.ScheduledTime = ScheduledTime;
        this.sessionToken = sessionToken;
    }

    public String getScheduledName() {
        return ScheduledName;
    }

    public String getScheduledTime() {
        return ScheduledTime;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}