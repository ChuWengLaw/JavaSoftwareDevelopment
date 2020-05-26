package Server.Reply;

import java.util.ArrayList;

public class GetCurrentScheduledReply {
    private String BillboardTitle;
    private boolean works;

    public GetCurrentScheduledReply(String BillboardTitle, boolean works){
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
