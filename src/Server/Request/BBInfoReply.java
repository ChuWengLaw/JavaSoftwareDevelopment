package Server.Request;

import java.io.Serializable;

public class BBInfoReply implements Serializable {
    private String information;

    public BBInfoReply(String info) {
        this.information = info;
    }

    public String getInformation() {
        return information;
    }
}
