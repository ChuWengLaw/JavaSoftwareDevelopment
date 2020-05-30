package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.CreateBBRequest;
import Server.Request.XmlRequest;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Base64;


/**
 * This class creates the GUI to be used to create a billboard
 */
public class CreateBillboardGUI extends JFrame {
    //define element to be used
    private JButton btnPreview;
    private JButton btnSubmit;
    private JButton btnBrowse;

    //define the labels
    private JLabel lblBillboardName;
    private JLabel lblTextColour;
    private JLabel lblBackgroundColour;
    private JLabel lblMessage;
    private JLabel lblImage;
    private JLabel lblInformation;
    private JLabel lblInformationColour;

    //define the text boxes
    private JTextField txtBillboardName;
    private JTextField txtTextColour;
    private JTextField txtBackgroundColour;
    private JTextField txtMessage;
    private JTextField txtImage;
    private JTextField txtInformation;
    private JTextField txtInformationColour;

    //define the strings to be used in the SQL
    private JPanel createBBPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints createBBConstraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation.
     *
     * @throws HeadlessException
     */
    public CreateBillboardGUI() throws HeadlessException {
        super("Create Billboard");
        createGUI();
    }

    /**
     * Create the base GUI to be used to create and edit the data
     *
     * @author Lachlan
     */
    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        //create the button and define what text it will contain
        btnPreview = createButton("Preview");
        //create and actionListener for the preview button
        btnPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //name is blank return appropriate message
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please enter a billboard name.");
                }
                //if name is more than one word return appropriate message
                else if (txtBillboardName.getText().contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please enter the name as one word.");
                }
                //if all colours input are valid proceed
                else if (isColourValid() && !txtBillboardName.getText().isBlank()) {
                    String path = "src/xmlBillboards/createpreview.xml";
                    try {
                        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
                        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                        Document document = documentBuilder.newDocument();
                        Element billboard = document.createElement("billboard");
                        document.appendChild(billboard);

                        if (txtBackgroundColour.getText().isBlank()) {
                            Attr background = document.createAttribute("background");
                            background.setValue("White");
                            billboard.setAttributeNode(background);
                        } else {
                            Attr background = document.createAttribute("background");
                            background.setValue(txtBackgroundColour.getText());
                            billboard.setAttributeNode(background);
                        }

                        if (!txtMessage.getText().isBlank()) {
                            //message element
                            Element bbMessage = document.createElement("message");
                            if (txtTextColour.getText().isBlank()) {
                                Attr textCol = document.createAttribute("colour");
                                textCol.setValue("Black");
                                bbMessage.setAttributeNode(textCol);
                            } else {
                                Attr textCol = document.createAttribute("colour");
                                textCol.setValue(txtTextColour.getText());
                                bbMessage.setAttributeNode(textCol);
                            }
                            bbMessage.appendChild(document.createTextNode(txtMessage.getText()));
                            billboard.appendChild(bbMessage);
                        }

                        if (!txtImage.getText().isBlank()) {
                            //picture element
                            Element pic = document.createElement("picture");
                            if (txtImage.getText().startsWith("http")) {
                                Attr picURL = document.createAttribute("url");
                                picURL.setValue(txtImage.getText());
                                pic.setAttributeNode(picURL);
                            } else {
                                Attr picData = document.createAttribute("data");
                                picData.setValue(txtImage.getText());
                                pic.setAttributeNode(picData);
                            }
                            billboard.appendChild(pic);
                        }

                        if (!txtInformation.getText().isBlank()) {
                            //information element
                            Element info = document.createElement("information");
                            if (txtInformationColour.getText().isBlank()) {
                                Attr infoColour = document.createAttribute("colour");
                                infoColour.setValue(txtInformationColour.getText());
                                info.setAttributeNode(infoColour);
                                info.appendChild(document.createTextNode(txtInformation.getText()));
                            } else {
                                Attr infoColour = document.createAttribute("colour");
                                infoColour.setValue(txtInformationColour.getText());
                                info.setAttributeNode(infoColour);
                                info.appendChild(document.createTextNode(txtInformation.getText()));
                            }
                            billboard.appendChild(info);
                        }
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource domSource = new DOMSource(document);
                        StreamResult streamResult = new StreamResult(new File(path));
                        transformer.transform(domSource, streamResult);
                        new PreviewBillboardGUI("createpreview");
                    } catch (ParserConfigurationException | TransformerConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        //create the button and define what text it will contain
        btnBrowse = createButton("Browse an image");
        //create and actionListener for the browse button
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
                jfc.setDialogTitle("Select a billboard xml file");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter bmp = new FileNameExtensionFilter("BMP", "bmp");
                FileNameExtensionFilter jpeg = new FileNameExtensionFilter("JPEG", "jpeg");
                FileNameExtensionFilter png = new FileNameExtensionFilter("PNG", "png");
                jfc.addChoosableFileFilter(bmp);
                jfc.addChoosableFileFilter(jpeg);
                jfc.addChoosableFileFilter(png);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    //Convert to base64 encoded
                    String encodstring = encodeFileBase64(selectedFile);
                    //Set the text field to the converted value
                    txtImage.setText(encodstring);
                    txtImage.setForeground(Color.black);
                }
            }
        });
        //create the button and define what text it will contain
        btnSubmit = createButton("Submit");
        //create and actionListener for the submit button
        btnSubmit.addActionListener(new ActionListener() {
            // sends request to server
            @Override
            public void actionPerformed(ActionEvent e) {
                //name is blank return appropriate message
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please enter a billboard name.");
                }
                //if name is more than one word return appropriate message
                else if (txtBillboardName.getText().contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please enter the name as one word.");
                }
                //if all colours input are valid proceed
                else if (isColourValid() && !txtBillboardName.getText().isBlank()) {
                    CreateBBRequest createBBRequest = new CreateBBRequest(Main.loginUser.getSessionToken(), txtBillboardName.getText().toLowerCase(), Main.loginUser.getUserName(), txtTextColour.getText(), txtBackgroundColour.getText(),
                            txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText(), Main.loginUser.getCreateBillboardsPermission());
                    try {
                        Client.connectServer(createBBRequest);
                        if (Client.isRequestState()) {
                            JOptionPane.showMessageDialog(null, "Billboard created!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to create billboard!");
                        }
                    } catch (ConnectException ex) {
                        JOptionPane.showMessageDialog(null, "Connection fail.");
                        System.exit(0);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Billboard fail to create.");
                    }
                    XmlRequest xmlRequest = new XmlRequest(Main.loginUser.getSessionToken(), txtBillboardName.getText(), false);
                    try {
                        Client.connectServer(xmlRequest);
                        if (Client.isRequestState()) {
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to create billboard xml!");
                        }
                    } catch (ConnectException ex) {
                        JOptionPane.showMessageDialog(null, "Connection fail.");
                        System.exit(0);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    //clear the textFeilds once the sumbit code has been executed
                    txtBillboardName.setText("");
                    txtTextColour.setText("");
                    txtBackgroundColour.setText("");
                    txtMessage.setText("");
                    txtImage.setText("");
                    txtInformation.setText("");
                    txtInformationColour.setText("");
                }
            }
        });

        //create the labels
        lblBillboardName = createLabel("Billboard Name:");
        lblTextColour = createLabel("Text Colour:");
        lblBackgroundColour = createLabel("Background Colour:");
        lblMessage = createLabel("Message:");
        lblImage = createLabel("Image");
        lblInformation = createLabel("Information:");
        lblInformationColour = createLabel("Information Colour:");

        //create the text boxes to receive the data
        txtBillboardName = createText();
        txtTextColour = createText();
        txtBackgroundColour = createText();
        txtMessage = createText();
        txtImage = createText();
        txtInformation = createText();
        txtInformationColour = createText();

        createBBConstraints.anchor = GridBagConstraints.WEST;
        createBBConstraints.insets = new Insets(10, 10, 10, 10);

        //add labels to panel
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 0;
        createBBPanel.add(lblBillboardName, createBBConstraints);
        createBBConstraints.gridy = 1;
        createBBPanel.add(lblTextColour, createBBConstraints);
        createBBConstraints.gridy = 2;
        createBBPanel.add(lblBackgroundColour, createBBConstraints);
        createBBConstraints.gridy = 3;
        createBBPanel.add(lblMessage, createBBConstraints);
        createBBConstraints.gridy = 4;
        createBBPanel.add(lblImage, createBBConstraints);
        createBBConstraints.gridy = 5;
        createBBPanel.add(lblInformation, createBBConstraints);
        createBBConstraints.gridy = 6;
        createBBPanel.add(lblInformationColour, createBBConstraints);

        //add txtfeilds to panel
        createBBConstraints.gridx = 1;
        createBBConstraints.gridy = 0;
        createBBPanel.add(txtBillboardName, createBBConstraints);
        createBBConstraints.gridy = 1;
        createBBPanel.add(txtTextColour, createBBConstraints);
        createBBConstraints.gridy = 2;
        createBBPanel.add(txtBackgroundColour, createBBConstraints);
        createBBConstraints.gridy = 3;
        createBBPanel.add(txtMessage, createBBConstraints);
        createBBConstraints.gridy = 4;
        createBBPanel.add(txtImage, createBBConstraints);
        createBBConstraints.gridy = 5;
        createBBPanel.add(txtInformation, createBBConstraints);
        createBBConstraints.gridy = 6;
        createBBPanel.add(txtInformationColour, createBBConstraints);

        //add button to panel
        createBBConstraints.gridwidth = 2;
        createBBConstraints.insets = new Insets(5, 10, 5, 10);
        createBBConstraints.anchor = GridBagConstraints.WEST;
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 8;
        createBBPanel.add(btnPreview, createBBConstraints);
        createBBConstraints.anchor = GridBagConstraints.CENTER;
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 8;
        createBBPanel.add(btnBrowse, createBBConstraints);
        createBBConstraints.anchor = GridBagConstraints.EAST;
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 8;
        createBBPanel.add(btnSubmit, createBBConstraints);

        getContentPane().add(createBBPanel);
        //set the location of the GUI
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);

        //make changes and then send to GUI
        pack();
        setVisible(true);
    }

    /**
     * This function creates a JButton
     *
     * @param text the text with will be on the button
     * @return a JButton with text on it
     * @author Lachlan
     */
    private JButton createButton(String text) {
        JButton button = new JButton();
        button.setText(text);
        return button;
    }

    /**
     * This function create a blank JTextField
     *
     * @return an empty JTextField
     * @author Lachlan
     */
    private JTextField createText() {
        JTextField textBox = new JTextField(20);
        return textBox;
    }

    /**
     * This function create a JLabel with the title of the text
     *
     * @param text the title of the label
     * @return a JLabel with the title of text
     * @author Lachlan
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }

    /**
     * This function determines whether a colour named using a code is valid or not
     *
     * @param textInput the input from the called text field
     * @return returns a boolean value of whether the color code is valid or not
     * @Lachlan
     */
    private boolean isColourCodeValid(String textInput) {
        boolean valid = false;
        boolean startsWithHash = false;
        String s2 = textInput.substring(1);

        if (textInput.length() == 7) {
            if (textInput.charAt(0) == '#') {
                startsWithHash = true;
            }
        }

        if (startsWithHash == true) {
            for (int i = 0; i < s2.length(); i++) {
                char c = s2.charAt(i);
                if (c != '#') {
                    if (s2.matches("[A-F]{1,}") || s2.matches("[0-9]{1,}")) {
                        valid = true;
                    } else {
                        valid = false;
                        break;
                    }
                }
            }
        }

        return valid;
    }

    /**
     * This function determines whether colours named by works are valid of not
     *
     * @return a boolean value to whether all the inputed colours are valid
     * @author Lachlan
     */
    private boolean isColourValid() {
        boolean text = true;
        boolean back = true;
        boolean info = true;

        //if the textColour isn't empty
        if (txtTextColour.getText().length() > 0) {
            //see if it starts with # and then see if it a valid colour code. If so set text to true else return a valid error message
            if (txtTextColour.getText().startsWith("#")) {
                if (isColourCodeValid(txtTextColour.getText())) {
                    text = true;
                } else {
                    text = false;
                    JOptionPane.showMessageDialog(null, "Please enter a valid text colour");
                }
            }
            //else if not valid colour name return appropriate error message
            else {
                try {
                    Class.forName("java.awt.Color").getField(txtTextColour.getText().toLowerCase());
                    text = true;
                } catch (NoSuchFieldException e) {
                    text = false;
                    JOptionPane.showMessageDialog(null, "Please enter a valid text colour");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        //if informationColour isn't empty
        if (txtInformationColour.getText().length() > 0) {
            //see if it starts with # and then see if it a valid colour code. If so set text to true else return a valid error message
            if (txtInformationColour.getText().startsWith("#")) {
                if (isColourCodeValid(txtInformationColour.getText())) {
                    info = true;
                } else {
                    info = false;
                    JOptionPane.showMessageDialog(null, "Please enter a valid information colour");
                }
            }
            //else if not valid colour name return appropriate error message
            else {
                try {
                    Class.forName("java.awt.Color").getField(txtInformationColour.getText().toLowerCase());
                    info = true;
                } catch (NoSuchFieldException e) {
                    info = false;
                    JOptionPane.showMessageDialog(null, "Please enter a valid information colour");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        //if backgroundColour isn't empty
        if (txtBackgroundColour.getText().length() > 0) {
            //see if it starts with # and then see if it a valid colour code. If so set text to true else return a valid error message
            if (txtBackgroundColour.getText().startsWith("#")) {
                if (isColourCodeValid(txtBackgroundColour.getText())) {
                    back = true;
                } else {
                    back = false;
                    JOptionPane.showMessageDialog(null, "Please enter a valid background colour");
                }
            }
            //else if not valid colour name return appropriate error message
            else {
                try {
                    Class.forName("java.awt.Color").getField(txtBackgroundColour.getText().toLowerCase());
                    back = true;
                } catch (NoSuchFieldException e) {
                    back = false;
                    JOptionPane.showMessageDialog(null, "Please enter a valid background colour");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return text && back && info;
    }

    private String encodeFileBase64(File file) {
        String encodedstring = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedstring = Base64.getEncoder().encodeToString(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedstring;
    }
}