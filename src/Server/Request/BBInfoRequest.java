package Server.Request;

import java.io.Serializable;

public class BBInfoRequest implements Serializable {
    private String BillboardName;

    public BBInfoRequest(String BillboardName) {
        this.BillboardName = BillboardName;
    }

    public String getBillboardName() {
        return BillboardName;
    }
}
