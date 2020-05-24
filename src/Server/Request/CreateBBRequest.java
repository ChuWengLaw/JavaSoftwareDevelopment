package Server.Request;

import java.io.Serializable;

public class CreateBBRequest implements Serializable {
    private String BillboardName, TextColour, BackgroundColour, Message, Image, Information, InformationColour;

    public CreateBBRequest(String BillboardName, String TextColour, String BackgroundColour,
                           String Message, String Image, String Information, String InformationColour) {
        this.BillboardName = BillboardName;
        this.TextColour = TextColour;
        this.BackgroundColour = BackgroundColour;
        this.Message = Message;
        this.Image = Image;
        this.Information= Information;
        this.InformationColour = InformationColour;
    }

    public String getBillboardName() {
        return BillboardName;
    }
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
}
