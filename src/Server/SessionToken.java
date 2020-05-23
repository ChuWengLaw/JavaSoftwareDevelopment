package Server;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SessionToken implements Serializable {
    private String sessionTokenString;
    private LocalDateTime usedTime;

    public SessionToken(String sessionToken, LocalDateTime usedTime){
        this.sessionTokenString = sessionToken;
        this.usedTime = usedTime;
    }

    public String getSessionTokenString() {
        return sessionTokenString;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime){
        this.usedTime = usedTime;
    }
}
