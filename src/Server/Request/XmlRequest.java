package Server.Request;

import java.io.Serializable;

public class XmlRequest implements Serializable {
    private String BillboardName;

    public XmlRequest(String BillboardName) {
        this.BillboardName = BillboardName;
    }

    public String getBillboardName() {
        return BillboardName;
    }
}