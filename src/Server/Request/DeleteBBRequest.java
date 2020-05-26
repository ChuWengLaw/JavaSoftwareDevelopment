package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class DeleteBBRequest implements Serializable {
    private String BillboardName, LoginUser;
    private SessionToken sessionToken;
    private Boolean EditAllBillboardsPermission, CreateBillboardPermission;

    public DeleteBBRequest(SessionToken sessionToken, String BillboardName, String LoginUser,
                           Boolean EditAllBillboardsPermission, Boolean CreateBillboardPermission) {
        this.BillboardName = BillboardName;
        this.sessionToken = sessionToken;
        this.LoginUser = LoginUser;
        this.CreateBillboardPermission = CreateBillboardPermission;
        this.EditAllBillboardsPermission = EditAllBillboardsPermission;
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
}
