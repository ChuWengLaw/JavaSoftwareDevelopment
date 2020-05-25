package Server.Request;

import ControlPanel.User;

import java.io.Serializable;

public class SearchReply implements Serializable {
    private boolean requestState;
    private User user;

    public SearchReply(boolean requestState){
        this.requestState = requestState;
    }

    public SearchReply(boolean requestState, User user){
        this.requestState = requestState;
        this.user = user;
    }

    public boolean isRequestState() {
        return requestState;
    }

    public User getUser() {
        return user;
    }
}
