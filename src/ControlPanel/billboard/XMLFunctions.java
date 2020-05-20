package ControlPanel.billboard;

import Server.Server;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLFunctions {

    /**
     * @param billboardName
     * @param textColour
     * @param backgroundColour
     * @param message
     * @param image
     * @param information
     * @param informationColour
     * @throws ParserConfigurationException
     * @author Lachlan
     */
    public void makeXML(String billboardName, String textColour, String backgroundColour,
                        String message, String image, String information, String informationColour) throws ParserConfigurationException {
        String path = "xmlBillboards/" + billboardName + ".xml";
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);

            Attr background = document.createAttribute("background");
            background.setValue(backgroundColour);
            billboard.setAttributeNode(background);

            //message element
            Element bbMessage = document.createElement("message");
            if (textColour == "") {
                Attr textCol = document.createAttribute("colour");
                textCol.setValue("Black");
                bbMessage.setAttributeNode(textCol);
            } else {
                Attr textCol = document.createAttribute("colour");
                textCol.setValue(textColour);
                bbMessage.setAttributeNode(textCol);
            }
            bbMessage.appendChild(document.createTextNode(message));
            billboard.appendChild(bbMessage);

            //picture element
            Element pic = document.createElement("picture");
            if (image.startsWith("http")) {
                Attr picURL = document.createAttribute("url");
                picURL.setValue(image);
                pic.setAttributeNode(picURL);
            } else {
                Attr picData = document.createAttribute("data");
                picData.setValue(image);
                pic.setAttributeNode(picData);
            }
            billboard.appendChild(pic);

            //information element
            Element info = document.createElement("information");
            if (informationColour == "") {
                Attr infoColour = document.createAttribute("colour");
                infoColour.setValue(textColour);
                info.setAttributeNode(infoColour);
                info.appendChild(document.createTextNode(information));
            } else {
                Attr infoColour = document.createAttribute("colour");
                infoColour.setValue(informationColour);
                info.setAttributeNode(infoColour);
                info.appendChild(document.createTextNode(information));
            }
            billboard.appendChild(info);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath
     * @author Lachlan
     */
    public void extractXML(String filePath) {
        String backgroundColour = null;
        String textColour = null;
        String message = null;
        String image = null;
        String information = null;
        String informationColour = null;
        try {
            File XmlFile = new File(filePath);
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
                        backgroundColour = element.getAttribute("background");
                    }
                    if (element.getTagName().startsWith("message")) {
                        message = element.getTextContent();
                        textColour = element.getAttribute("colour");
                    }
                    if (element.getTagName().startsWith("information")) {
                        information = element.getTextContent();
                        informationColour = element.getAttribute("colour");
                    }
                }
            }

            System.out.println(message);
            System.out.println(image);
            System.out.println(backgroundColour);
            System.out.println(textColour);
            System.out.println(information);
            System.out.println(informationColour);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
