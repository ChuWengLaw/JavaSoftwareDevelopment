package Server.Reply;

import Server.SessionToken;

import java.io.File;
import java.io.Serializable;

public class XmlReply implements Serializable {
    private SessionToken sessionToken;
    private File billboardXml;

    public XmlReply(SessionToken sessionToken, File billboardXml) {
        this.sessionToken = sessionToken;
        this.billboardXml = billboardXml;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public File getBillboardXml() {
        return billboardXml;
    }
}