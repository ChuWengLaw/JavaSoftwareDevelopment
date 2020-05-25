package Server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;

public class ExtractFromXML {

    public Color backgroundColour;
    public String BGColourStr;
    public String TxtColourStr;
    public Color textColour;
    public String message;
    public String image;
    public String information;
    public Color informationColour;
    public String InfoColourStr;

    /**
     * This constructor takes the xml file name and extracts the data from the xml and
     * putting the into the public strings above
     *
     * @param fileName the part of the name before the .xml
     * @author Lachlan
     */
    public ExtractFromXML(String fileName) {
        Color backgroundColour = null;
        Color textColour = null;
        String message = "";
        String image = "";
        String information = "";
        Color informationColour = null;

        try {
            File XmlFile = new File("src/xmlBillboards/" + fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(XmlFile);

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("*");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getTagName().startsWith("picture")) {
                        if (element.hasAttribute("url")) {
                            image = element.getAttribute("url");
                        }
                        if (element.hasAttribute("data")) {
                            image = element.getAttribute("data");
                        }
                    }
                    if (element.getTagName().startsWith("billboard")) {
                        Field backGroundColourField = Class.forName("java.awt.Color").getField(element.getAttribute("background").toLowerCase());
                        backgroundColour = (Color) backGroundColourField.get(null);
                        BGColourStr = (String) backGroundColourField.get(null);
                    }
                    if (element.getTagName().startsWith("message")) {
                        message = element.getTextContent();
                        Field textColourField = Class.forName("java.awt.Color").getField(element.getAttribute("colour").toLowerCase());
                        textColour = (Color) textColourField.get(null);
                        TxtColourStr = (String) textColourField.get(null);
                    }
                    if (element.getTagName().startsWith("information")) {
                        information = element.getTextContent();
                        Field infoColourField = Class.forName("java.awt.Color").getField(element.getAttribute("colour").toLowerCase());
                        informationColour = (Color) infoColourField.get(null);
                        InfoColourStr = (String) infoColourField.get(null);
                    }
                }
            }
            this.backgroundColour = backgroundColour;
            this.textColour = textColour;
            this.message = message;
            this.image = image;
            this.information = information;
            this.informationColour = informationColour;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

