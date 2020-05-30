package Server.Reply;

import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a reply class that handle general reply, it requires
 * an updated session token and a boolean value indicates
 * the request execute state.
 */
public class GeneralReply implements Serializable{
    // Initialize all require variables.
    private SessionToken sessionToken;
    private boolean requestState;

    /**
     * This is constructor
     * @param sessionToken an updated session token
     * @param requestState the request state
     */
    public GeneralReply(SessionToken sessionToken, boolean requestState){
        this.sessionToken = sessionToken;
        this.requestState = requestState;
    }

    //build constructor and Getter functions
    public boolean isRequestState() {
        return requestState;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
