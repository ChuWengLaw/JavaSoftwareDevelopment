package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class CreateBBRequest implements Serializable {
    private SessionToken sessionToken;
    private Boolean CreateBillboardPermission;
    private String BillboardName, UserName, TextColour, BackgroundColour, Message, Image, Information, InformationColour;

    public CreateBBRequest(SessionToken sessionToken, String BillboardName, String UserName, String TextColour, String BackgroundColour,
                           String Message, String Image, String Information, String InformationColour, Boolean CreateBillboardPermission) {
        this.sessionToken = sessionToken;
        this.BillboardName = BillboardName;
        this.UserName = UserName;
        this.TextColour = TextColour;
        this.BackgroundColour = BackgroundColour;
        this.Message = Message;
        this.Image = Image;
        this.Information= Information;
        this.InformationColour = InformationColour;
        this.CreateBillboardPermission = CreateBillboardPermission;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
    public String getBillboardName() {
        return BillboardName;
    }
    public String getUserName() { return UserName; }
    public String getTextColour() { return TextColour; }
    public String getBackgroundColour() {
        return BackgroundColour;
    }
    public String getMessage() {
        return Message;
    }
    public String getImage() {
        return Image;
    }
    public String getInformation() {
        return Information;
    }
    public String getInformationColour() { return InformationColour; }

    public Boolean getCreateBillboardPermission() {
        return CreateBillboardPermission;
    }
}
