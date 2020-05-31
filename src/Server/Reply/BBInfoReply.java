package Server.Reply;

import Server.SessionToken;

import java.io.Serializable;

public class BBInfoReply implements Serializable {
    private SessionToken token;
    private String EditTextColour, EditBGColour, EditMsg, EditImg, EditInfo, EditInfoColour;

    //build constructor and Getter functions
    public BBInfoReply(SessionToken token, String EditTextColour, String EditBGColour, String EditMsg, String EditImg, String EditInfo, String EditInfoColour) {
        this.token = token;
        this.EditTextColour = EditTextColour;
        this.EditBGColour = EditBGColour;
        this.EditMsg = EditMsg;
        this.EditImg = EditImg;
        this.EditInfo = EditInfo;
        this.EditInfoColour = EditInfoColour;
    }

    public String getEditBGColour() {
        return EditBGColour;
    }

    public String getEditTextColour() {
        return EditTextColour;
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

    public SessionToken getSessionToken() {
        return token;
    }
}
