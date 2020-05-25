package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class EditBBRequest implements Serializable {
    private String BillboardName;
    private SessionToken sessionToken;

    public EditBBRequest(SessionToken sessionToken, String BillboardName) {
        this.BillboardName = BillboardName;
        this.sessionToken = sessionToken;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
    public String getBillboardName() {
        return BillboardName;
    }
}

