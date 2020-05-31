package Server;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Nicholas Tseng
 * This is the session token class that will hold a random string
 * as the name of the session token and a LocalDateTime
 * which indicates the time session token was last used.
 */
public class SessionToken implements Serializable {
    // Initialize all variables for a session token.
    private String sessionTokenString;
    private LocalDateTime usedTime;

    public SessionToken(String sessionToken, LocalDateTime usedTime) {
        this.sessionTokenString = sessionToken;
        this.usedTime = usedTime;
    }

    public String getSessionTokenString() {
        return sessionTokenString;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime) {
        this.usedTime = usedTime;
    }
}
