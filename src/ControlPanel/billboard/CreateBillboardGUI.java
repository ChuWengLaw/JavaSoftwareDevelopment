package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.CreateBBRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class creates the GUI to be used to create a billboard
 */
public class CreateBillboardGUI extends JFrame {
    //define element to be used
    private JButton btnSubmit;

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
    private String strBillboardName;
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
                if (txtBillboardName.getText().contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please Enter the Name as One Word");
                }
                //if any of the colours are a have a code value
                //if text colour is a code
                if (txtTextColour.getText().startsWith("#")) {
                    if (!isColourValid(txtTextColour.getText())) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid text colour");
                    }
                }
                //if background is a code
                if (txtBackgroundColour.getText().startsWith("#")) {
                    if (!isColourValid(txtBackgroundColour.getText())) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid background colour");
                    }
                }
                //if info colour is a code
                if (txtInformationColour.getText().startsWith("#")) {
                    if (!isColourValid(txtInformationColour.getText())) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid information colour");
                    }
                }

                if (txtTextColour.getText().length()>0 && !txtTextColour.getText().startsWith("#")){
                    try{
                        Class.forName("java.awt.Color").getField(txtTextColour.getText());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid text colour");
                    }
                }

                if (txtBackgroundColour.getText().length()>0 && !txtBackgroundColour.getText().startsWith("#")){
                    try{
                        Class.forName("java.awt.Color").getField(txtBackgroundColour.getText());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid background colour");
                    }
                }

                if (txtInformationColour.getText().length()>0 && !txtInformationColour.getText().startsWith("#")){
                    try{
                        Class.forName("java.awt.Color").getField(txtInformationColour.getText());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid information colour");
                    }
                }

                //if not issues then proceed
                else {
                    CreateBBRequest temp = new CreateBBRequest(Main.loginUser.getSessionToken(), txtBillboardName.getText(), Main.loginUser.getUserName(), txtTextColour.getText(), txtBackgroundColour.getText(),
                            txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText());
                    try {
                        Client.connectServer(temp);

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
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
        createBBConstraints.anchor = GridBagConstraints.EAST;
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 8;
        createBBPanel.add(btnSubmit, createBBConstraints);

        getContentPane().add(createBBPanel);
        //set the location of the GUI
        setLocation(900, 350);

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
     * This function create a JTextField with the input of the text
     *
     * @param text the text to be displayed
     * @return a JTextField with the input of text
     * @author Lachlan
     */
    private JTextField createText(String text) {
        JTextField textBox = new JTextField();
        textBox.setText(text);
        strBillboardName = text;
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

    private boolean isColourValid(String codeInput) {
        boolean valid = false;
        boolean startsWithHash = false;
        String s2 = codeInput.substring(1);

        if (codeInput.length() == 7) {
            if (codeInput.charAt(0) == '#') {
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

//    /**
//     * This function is used to determine if a billboard already exists
//     *
//     * @param billboardName the name of the billboard being created
//     * @return a boolean value to whether a billboard already exists
//     * @throws SQLException
//     * @author Lachlan
//     */
//    private Boolean checkDublicate(String billboardName) throws SQLException {
//        boolean existing = false;
//
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery("SELECT BillboardName FROM Billboard");
//
//        while (rs.next()) {
//            if (billboardName.equals(rs.getString(1))) {
//                existing = true;
//                break;
//            }
//        }
//        statement.close();
//        return existing;
//    }
}