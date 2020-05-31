package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class DeleteBBRequest implements Serializable {
    private String BillboardName, LoginUser;
    private SessionToken sessionToken;
    private Boolean EditAllBillboardsPermission, CreateBillboardPermission,IsScheduled;

    //build constructor and Getter functions
    public DeleteBBRequest(SessionToken sessionToken, String BillboardName, String LoginUser,
                           Boolean EditAllBillboardsPermission, Boolean CreateBillboardPermission, Boolean IsScheduled) {
        this.BillboardName = BillboardName;
        this.sessionToken = sessionToken;
        this.LoginUser = LoginUser;
        this.CreateBillboardPermission = CreateBillboardPermission;
        this.EditAllBillboardsPermission = EditAllBillboardsPermission;
        this.IsScheduled = IsScheduled;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public String getBillboardName() {
        return BillboardName;
    }

    public Boolean getEditAllBillboardsPermission() {
        return EditAllBillboardsPermission;
    }

    public Boolean getCreateBillboardPermission() {
        return CreateBillboardPermission;
    }

    public String getLoginUser() {
        return LoginUser;
    }

    public Boolean getIsScheduled() {
        return IsScheduled;
    }
}
