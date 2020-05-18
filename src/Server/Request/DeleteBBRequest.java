package Server.Request;

import java.io.Serializable;

public class DeleteBBRequest implements Serializable {
    private String BillboardName;

    public DeleteBBRequest(String BillboardName) {
        this.BillboardName = BillboardName;
    }

    public String getBillboardName() {
        return BillboardName;
    }
}
