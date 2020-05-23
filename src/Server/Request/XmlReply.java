package Server.Request;

import java.io.Serializable;

public class XmlReply implements Serializable {
    private String BillboardName;

    public XmlReply(String BillboardName) {
        this.BillboardName = BillboardName;
    }

    public String getBillboardName() {
        return BillboardName;
    }
}