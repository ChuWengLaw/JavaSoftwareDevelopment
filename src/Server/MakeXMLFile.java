package Server;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import java.sql.ResultSet;
import java.sql.SQLException;

public class MakeXMLFile {
    private String BillboardName, textColour, backgroundColour, message, image, information, informationColour;

    /**
     * This method writes the billboard contents into XML format
     *
     * @param billboardName
     * @author Lachlan
     */
    public MakeXMLFile(String billboardName) throws SQLException {
        String path = "src/xmlBillboards/" + billboardName + ".xml";
        BillboardName = billboardName;
        ExecuteSQL();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);

            if (backgroundColour.isBlank()) {
                Attr background = document.createAttribute("background");
                background.setValue("White");
                billboard.setAttributeNode(background);
            } else {
                Attr background = document.createAttribute("background");
                background.setValue(backgroundColour);
                billboard.setAttributeNode(background);
            }

            if (!message.isBlank()) {
                //message element
                Element bbMessage = document.createElement("message");
                if (textColour.isBlank()) {
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
            }

            if (!image.isBlank()) {
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
            }

            if (!information.isBlank()) {
                //information element
                Element info = document.createElement("information");
                if (informationColour.isBlank()) {
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
            }

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
     * This method executes SQL to extract the billboard contents
     *
     * @author Law
     */
    private void ExecuteSQL() throws SQLException {
        ResultSet getBillboard = Server.statement.executeQuery("SELECT * FROM Billboard WHERE BillboardName = '"
                + BillboardName + "';");
        getBillboard.next();
        backgroundColour = getBillboard.getString("BackGroundColour");
        message = getBillboard.getString("Message");
        textColour = getBillboard.getString("TextColour");
        image = getBillboard.getString("Image");
        information = getBillboard.getString("Information");
        informationColour = getBillboard.getString("InfoColour");
    }
}
