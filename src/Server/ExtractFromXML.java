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
                    //if picture exists...
                    if (element.getTagName().startsWith("picture")) {
                        //if picture has attribute url then set image to that url
                        if (element.hasAttribute("url")) {
                            image = element.getAttribute("url");
                        }
                        //if the picture has attribute data then set image to that data
                        if (element.hasAttribute("data")) {
                            image = element.getAttribute("data");
                        }
                    }
                    //if the billboard tag exists...
                    if (element.getTagName().startsWith("billboard")) {
                        //if billboard has background atribute
                        if (element.hasAttribute("background")) {
                            BGColourStr = element.getAttribute("background");
                            //if the colour is a colour code then decode the color and store  as the background colour
                            if (BGColourStr.startsWith("#")) {
                                backgroundColour = Color.decode(BGColourStr);
                            }
                            //else convert a colour string of the colour to colour
                            else {
                                Field backGroundColourField = Class.forName("java.awt.Color").getField(element.getAttribute("background").toLowerCase());
                                backgroundColour = (Color) backGroundColourField.get(null);
                            }
                        }
                        //if no colour exist set background to white
                        else {
                            backgroundColour = Color.white;
                        }

                    }
                    //if the message tag exists...
                    if (element.getTagName().startsWith("message")) {
                        message = element.getTextContent();
                        //if message has the attribute colour then ...
                        if (element.hasAttribute("colour")) {
                            TxtColourStr = element.getAttribute("colour");
                            //if colour is a code then decode the colour code
                            if (TxtColourStr.startsWith("#")) {
                                textColour = Color.decode(TxtColourStr);
                            }
                            //if the colour is a string the convert to a colour field
                            else {
                                Field textColourField = Class.forName("java.awt.Color").getField(element.getAttribute("colour").toLowerCase());
                                textColour = (Color) textColourField.get(null);
                            }
                        }
                        //if no colour exist set it to black
                        else textColour = Color.black;

                    }
                    //if the information tag exists...
                    if (element.getTagName().startsWith("information")) {
                        information = element.getTextContent();
                        //if the tag has the colour atribute ...
                        if (element.hasAttribute("colour")) {
                            InfoColourStr = element.getAttribute("colour");
                            //if colour is a code then decode the colour
                            if (InfoColourStr.startsWith("#")) {
                                informationColour = Color.decode(InfoColourStr);
                            }
                            //if the colour isn't a code then convert the string
                            else {
                                Field infoColourField = Class.forName("java.awt.Color").getField(element.getAttribute("colour").toLowerCase());
                                informationColour = (Color) infoColourField.get(null);
                            }
                        }
                        //if colour doesn't exist make it the same as the the text colour
                        else {
                            informationColour = textColour;
                        }

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