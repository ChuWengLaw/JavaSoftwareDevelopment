package Server.Request;

import java.io.File;
import java.io.Serializable;

public class XmlRequest implements Serializable {
    private String xmlName, UserName;
    private File xmlFile;

    public XmlRequest(File getXmlFile, String UserName) {
        this.xmlFile = getXmlFile;
        this.UserName = UserName;
    }

    public XmlRequest(String xmlName) {
        this.xmlName = xmlName;
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
}