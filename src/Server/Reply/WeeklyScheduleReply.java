package Server.Reply;

import Server.SessionToken;

import java.io.Serializable;
import java.util.ArrayList;

public class WeeklyScheduleReply implements Serializable {
    private SessionToken sessionToken;
    private ArrayList<String[]> Array;

    public WeeklyScheduleReply(SessionToken sessionToken, ArrayList<String[]> Array){
        this.sessionToken = sessionToken;
        this.Array = Array;
    }

    public WeeklyScheduleReply(ArrayList<String[]> scheduledInformation) {
    }

    public ArrayList<String[]> getArray() {
        return Array;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}