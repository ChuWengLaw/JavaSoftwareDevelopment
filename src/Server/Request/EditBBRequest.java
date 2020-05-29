package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class EditBBRequest implements Serializable {
    private String BillboardName, LoginUser, EditTextColour, EditBGColour, EditMsg, EditImg, EditInfo, EditInfoColour;
    private Boolean EditAllBillboardsPermission, CreateBillboardPermission, IsScheduled;
    private SessionToken sessionToken;

    public EditBBRequest(SessionToken sessionToken, String LoginUser, String BillboardName,
                         Boolean EditAllBillboardsPermission, Boolean CreateBillboardPermission, Boolean IsScheduled) {
        this.sessionToken = sessionToken;
        this.LoginUser = LoginUser;
        this.BillboardName = BillboardName;
        this.EditAllBillboardsPermission = EditAllBillboardsPermission;
        this.CreateBillboardPermission = CreateBillboardPermission;
        this.IsScheduled = IsScheduled;
    }
    public EditBBRequest(SessionToken sessionToken, String EditTextColour, String EditBGColour, String EditMsg,
                         String EditImg, String EditInfo, String EditInfoColour) {
        this.sessionToken = sessionToken;
        this.EditTextColour = EditTextColour;
        this.EditBGColour = EditBGColour;
        this.EditMsg = EditMsg;
        this.EditImg = EditImg;
        this.EditInfo = EditInfo;
        this.EditInfoColour = EditInfoColour;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
    public String getEditTextColour() {
        return EditTextColour;
    }
    public String getEditBGColour() {
        return EditBGColour;
    }
    public String getEditMsg() {
        return EditMsg;
    }
    public String getEditImg() {
        return EditImg;
    }
    public String getEditInfo() {
        return EditInfo;
    }
    public String getEditInfoColour() {
        return EditInfoColour;
    }
    public String getBillboardName() {
        return BillboardName;
    }
    public String getLoginUser() {
        return LoginUser;
    }

    public Boolean getEditAllBillboardsPermission() {
        return EditAllBillboardsPermission;
    }

    public Boolean getCreateBillboardPermission() {
        return CreateBillboardPermission;
    }

    public Boolean getIsScheduled() {
        return IsScheduled;
    }
}

