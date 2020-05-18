package Server.Request;

import ControlPanel.User;

import java.io.Serializable;

public class GernalReply implements Serializable{
    private boolean requestState;

    public GernalReply(boolean requestState){
        this.requestState = requestState;
    }

    public boolean isRequestState() {
        return requestState;
    }
}
