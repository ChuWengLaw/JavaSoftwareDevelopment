package Server.Reply;

import Server.SessionToken;

import java.io.Serializable;

public class BBInfoReply implements Serializable {
    private SessionToken sessionToken;
    private String information;

    public BBInfoReply(SessionToken sessionToken, String info) {
        this.sessionToken = sessionToken;
        this.information = info;
    }

    public String getInformation() {
        return information;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
