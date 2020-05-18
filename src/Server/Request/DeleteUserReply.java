package Server.Request;

import java.io.Serializable;

public class DeleteUserReply implements Serializable {
    private boolean DeleteState;
    public DeleteUserReply(boolean DeleteState){
        this.DeleteState = DeleteState;
    }

    public boolean isDeletestate() {
        return DeleteState;
    }
}
