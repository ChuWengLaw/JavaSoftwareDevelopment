package Server.Request;

import java.io.Serializable;

public class LogoutReply implements Serializable {
    private boolean expired;

    public LogoutReply(boolean expired) {
        this.expired = expired;
    }

    public boolean isExpired() {
        return expired;
    }
}
