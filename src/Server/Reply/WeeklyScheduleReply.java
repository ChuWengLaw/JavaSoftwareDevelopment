package Server.Reply;

import Server.SessionToken;

import java.io.Serializable;
import java.util.ArrayList;

public class WeeklyScheduleReply implements Serializable {
    private SessionToken sessionToken;
    private ArrayList<String[]> Array;

    /**
     * @param sessionToken
     * @param Array        This is the reply class that handle the weekly schedule reply.
     *                     It takes the sessionToken, array of all the schedules
     * @author Callum Longman
     */
    //build constructor and Getter functions
    public WeeklyScheduleReply(SessionToken sessionToken, ArrayList<String[]> Array) {
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