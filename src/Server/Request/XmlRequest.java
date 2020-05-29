package Server.Request;

import Server.SessionToken;

import java.io.File;
import java.io.Serializable;

public class XmlRequest implements Serializable {
    private String xmlName, UserName;
    private File xmlFile;
    private SessionToken token;
    private boolean exportState;

    public XmlRequest(SessionToken token, File getXmlFile, String UserName) {
        this.token = token;
        this.xmlFile = getXmlFile;
        this.UserName = UserName;
    }

    public XmlRequest(SessionToken token, String xmlName, boolean exportState) {
        this.token = token;
        this.xmlName = xmlName;
        this.exportState = exportState;
    }

    public File getXmlFile() {
        return xmlFile;
    }

    public String getXmlName() {
        return xmlName;
    }

    public String getUserName() {
        return UserName;
    }

    public SessionToken getSessionToken() {
        return token;
    }

    public boolean isExportState() {
        return exportState;
    }
}