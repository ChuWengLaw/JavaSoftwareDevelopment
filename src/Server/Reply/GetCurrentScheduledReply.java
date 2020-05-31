package Server.Reply;

import java.io.Serializable;
import java.util.ArrayList;

public class GetCurrentScheduledReply implements Serializable {
    private String BillboardTitle;
    private boolean works;

    //build constructor and Getter functions
    public GetCurrentScheduledReply(String BillboardTitle, boolean works) {
        this.BillboardTitle = BillboardTitle;
        this.works = works;
    }

    public String getBillboardTitle() {
        return BillboardTitle;
    }

    public boolean getboolworks() {
        return works;
    }

}
