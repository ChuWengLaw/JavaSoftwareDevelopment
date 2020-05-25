package Server.Request;

import java.io.Serializable;

public class GeneralReply implements Serializable{
    private boolean requestState;

    public GeneralReply(boolean requestState){
        this.requestState = requestState;
    }

    public boolean isRequestState() {
        return requestState;
    }
}
