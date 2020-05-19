package ControlPanel.billboard;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CreateXML {

    public void makeXML(String billboardName, String textColour, String backgroundColour,
                        String message, String image, String information) throws ParserConfigurationException {
        String path = "xmlBillboards"+billboardName+".xml";
        try
        {DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);

            Attr background = document.createAttribute("background");
            background.setValue(backgroundColour);
            billboard.setAttributeNode(background);

            //message element
            Element bbMessage = document.createElement("message");
            Attr textCol = document.createAttribute("colour");
            textCol.setValue(textColour);
            bbMessage.setAttributeNode(textCol);
            bbMessage.appendChild(document.createTextNode(message));
            billboard.appendChild(bbMessage);

            //picture element
            Element pic = document.createElement("picture");
            if(image.startsWith("http")){
                Attr picURL = document.createAttribute("url");
                picURL.setValue(image);
                pic.setAttributeNode(picURL);
            } else{
               Attr picData = document.createAttribute("data");
               picData.setValue(image);
               pic.setAttributeNode(picData);
            }
            billboard.appendChild(pic);

            //information element
            Element info = document.createElement("information");
            Attr infoColour = document.createAttribute("colour");
            infoColour.setValue(textColour);
            info.setAttributeNode(infoColour);
            billboard.appendChild(info);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));

            transformer.transform(domSource,streamResult);

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
