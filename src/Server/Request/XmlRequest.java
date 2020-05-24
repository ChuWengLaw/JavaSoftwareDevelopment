package Server.Request;

import java.io.File;
import java.io.Serializable;

public class XmlRequest implements Serializable {
    private String xmlName;
    private File xmlFile;

    public XmlRequest(File getXmlFile) {
        this.xmlFile = getXmlFile;
    }
    public XmlRequest(String xmlName) {
        this.xmlName = xmlName;
    }
    public File getXmlFile() { return xmlFile; }
    public String getXmlName() { return xmlName; }
}