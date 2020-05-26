package Server.Request;

import Server.SessionToken;

import java.io.File;
import java.io.Serializable;

public class XmlRequest implements Serializable {
    private String xmlName, UserName;
    private File xmlFile;
    private SessionToken token;

    public XmlRequest(SessionToken token, File getXmlFile, String UserName) {
        this.token = token;
        this.xmlFile = getXmlFile;
        this.UserName = UserName;
    }
    public XmlRequest(SessionToken token, String xmlName) {
        this.token = token;
        this.xmlName = xmlName;
    }
    public File getXmlFile() { return xmlFile; }
    public String getXmlName() { return xmlName; }
    public String getUserName() { return UserName; }
    public SessionToken getSessionToken() {
        return token;
    };
}