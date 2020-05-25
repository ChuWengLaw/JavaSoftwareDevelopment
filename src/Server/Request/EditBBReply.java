package Server.Request;

import java.io.Serializable;

public class EditBBReply implements Serializable {
    private String EditTextColour, EditBGColour, EditMsg, EditImg, EditInfo, EditInfoColour;

    public EditBBReply(String EditTextColour, String EditBGColour, String EditMsg, String EditImg, String EditInfo, String EditInfoColour) {
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
}