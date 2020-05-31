package Server.Reply;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a reply class that handle logout reply, it requires
 * a boolean that indicates if user is forced to log out
 * due to expired session token.
 */
public class LogoutReply implements Serializable {
    // Initialize all require variables.
    private boolean expired;

    /**
     * This is constructor.
     *
     * @param expired a boolean that indicates if user is forced to log out
     *                due to expired session token
     */
    public LogoutReply(boolean expired) {
        this.expired = expired;
    }

    public boolean isExpired() {
        return expired;
    }
}
