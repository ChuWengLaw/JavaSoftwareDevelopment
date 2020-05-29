package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import ControlPanel.schedule.CalanderScheduleGUI;
import Server.Request.EditBBRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;

/**
 * This class creates the GUI to be used to edit a billboard
 */
public class EditBillboardGUI extends JFrame {
    //set the width of the GUI
    public static final int WIDTH = 350;
    public static final int HEIGHT = 400;

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
    private JPanel editBBPanel = new JPanel(new GridBagLayout());
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints editBBConstraints = new GridBagConstraints();
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation.
     *
     * @throws HeadlessException
     */
    public EditBillboardGUI() throws HeadlessException {
        super("Edit Billboard");
        createGUI();
    }

    /**
     * Create the base GUI to be used to find the billboard
     *
     * @author Law
     */
    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the labels
        lblBillboardName = createLabel("Billboard Name:");

        //create the text boxes to receive the data
        txtBillboardName = createText();

        //create the button and define what text it will contain
        btnSubmit = createButton("Submit");

        //create and actionListener for the submit button
        btnSubmit.addActionListener(new ActionListener() {
            // sends request to server
            @Override
            public void actionPerformed(ActionEvent e) {
                EditBBRequest temp = new EditBBRequest(Main.loginUser.getSessionToken(), Main.loginUser.getUserName(),
                        txtBillboardName.getText().toLowerCase(), Main.loginUser.getEditAllBillboardPermission(),
                        Main.loginUser.getCreateBillboardsPermission(), CalanderScheduleGUI.IsCurrentlyScheduled(txtBillboardName.getText()));

                try {
                    Client.connectServer(temp);
                    if(Client.isRequestState()){
                        JOptionPane.showMessageDialog(null, "Opening edit window...");
                        // hide the previous panel
                        panel.setVisible(false);
                        btnSubmit.setVisible(false);
                        lblBillboardName.setVisible(false);
                        txtBillboardName.setVisible(false);
                        createEditGUI();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No permission/invalid billboard name/Currently Scheduled");
                    }
                } catch(ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection fail.");
                    System.exit(0);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2, 2, 2, 2);

        //add labels to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lblBillboardName, constraints);
        constraints.gridy = 1;

        //add text fields to panel
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(txtBillboardName, constraints);

        //add button to panel
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(btnSubmit, constraints);

        getContentPane().add(panel);
        //set the location of the GUI
        setLocation(900, 350);

        //make changes and then send to GUI
        pack();
        setVisible(true);
    }

    /**
     * Create the base GUI to be used to edit the billboard
     *
     * @author Law
     */
    private void createEditGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and define what text it will contain
        btnSubmit = createButton("Submit");
        //create and actionListener for the submit button
        btnSubmit.addActionListener(new ActionListener() {
            // sends request to server
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean txtClr = false, bgClr = false, infoClr = false;
                if (!(txtTextColour.getText().isBlank() && txtInformationColour.getText().isBlank() && txtBackgroundColour.getText().isBlank())) {
                    try {
                        Class.forName("java.awt.Color").getField(txtTextColour.getText());
                        txtClr = true;
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the text colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Class.forName("java.awt.Color").getField(txtBackgroundColour.getText());
                        bgClr = true;
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the background colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Class.forName("java.awt.Color").getField(txtInformationColour.getText());
                        infoClr = true;
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the information colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if (!(txtTextColour.getText().isBlank() && txtBackgroundColour.getText().isBlank())) {
                    try {
                        Class.forName("java.awt.Color").getField(txtTextColour.getText());
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the text colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Class.forName("java.awt.Color").getField(txtBackgroundColour.getText());
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the background colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if (!(txtTextColour.getText().isBlank() && txtInformationColour.getText().isBlank())) {
                    try {
                        Class.forName("java.awt.Color").getField(txtTextColour.getText());
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the text colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Class.forName("java.awt.Color").getField(txtInformationColour.getText());
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the information colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if (!(txtInformationColour.getText().isBlank() && txtBackgroundColour.getText().isBlank())) {
                    try {
                        Class.forName("java.awt.Color").getField(txtBackgroundColour.getText());
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the background colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Class.forName("java.awt.Color").getField(txtInformationColour.getText());
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the information colour field");
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else if (!txtTextColour.getText().isBlank()) {
                    try {
                        Class.forName("java.awt.Color").getField(txtTextColour.getText());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the text colour field");
                    }
                } else if (!txtBackgroundColour.getText().isBlank()) {
                    try {
                        Class.forName("java.awt.Color").getField(txtBackgroundColour.getText());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the background colour field");
                    }
                } else if (!txtInformationColour.getText().isBlank()) {
                    try {
                        Class.forName("java.awt.Color").getField(txtInformationColour.getText());
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchFieldException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid colour into the information colour field");
                    }
                }
                // if the colour texts are all valid, send request to server
                if (txtClr && bgClr && infoClr) {
                    EditBBRequest temp = new EditBBRequest(Main.loginUser.getSessionToken(), txtTextColour.getText(), txtBackgroundColour.getText(),
                            txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText());
                    try {
                        Client.connectServer(temp);
                        if(Client.isRequestState()){
                            JOptionPane.showMessageDialog(null, "Successfully edited billboard!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Fail to edit billboard!");
                        }
                    } catch(ConnectException ex) {
                        JOptionPane.showMessageDialog(null, "Connection fail.");
                        System.exit(0);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    // clear the text fields once the SQL code has been executed
                    txtBillboardName.setText("");
                    txtTextColour.setText("");
                    txtBackgroundColour.setText("");
                    txtMessage.setText("");
                    txtImage.setText("");
                    txtInformation.setText("");
                    txtInformationColour.setText("");
                    setVisible(false);
                }
            }
        });


        //create the labels
        lblTextColour = createLabel("Text Colour:");
        lblBackgroundColour = createLabel("Background Colour:");
        lblMessage = createLabel("Message:");
        lblImage = createLabel("Image");
        lblInformation = createLabel("Information:");
        lblInformationColour = createLabel("Information Colour:");

        //create the text boxes to receive the data
        txtTextColour = createText();
        txtBackgroundColour = createText();
        txtMessage = createText();
        txtImage = createText();
        txtInformation = createText();
        txtInformationColour = createText();

        //set the content of billboard in text field
        txtTextColour.setText(Client.getEditTextColour());
        txtBackgroundColour.setText(Client.getEditBGColour());
        txtMessage.setText(Client.getEditMsg());
        txtImage.setText(Client.getEditImg());
        txtInformation.setText(Client.getEditInfo());
        txtInformationColour.setText(Client.getEditInfoColour());
        editBBConstraints.anchor = GridBagConstraints.WEST;
        editBBConstraints.insets = new Insets(10, 10, 10, 10);

        //add labels to panel
        editBBConstraints.gridx = 0;
        editBBConstraints.gridy = 0;
        editBBPanel.add(lblTextColour, editBBConstraints);
        editBBConstraints.gridy = 1;
        editBBPanel.add(lblBackgroundColour, editBBConstraints);
        editBBConstraints.gridy = 2;
        editBBPanel.add(lblMessage, editBBConstraints);
        editBBConstraints.gridy = 3;
        editBBPanel.add(lblImage, editBBConstraints);
        editBBConstraints.gridy = 4;
        editBBPanel.add(lblInformation, editBBConstraints);
        editBBConstraints.gridy = 5;
        editBBPanel.add(lblInformationColour, editBBConstraints);

        //add txtfeilds to panel
        editBBConstraints.gridx = 1;
        editBBConstraints.gridy = 0;
        editBBPanel.add(txtTextColour, editBBConstraints);
        editBBConstraints.gridy = 1;
        editBBPanel.add(txtBackgroundColour, editBBConstraints);
        editBBConstraints.gridy = 2;
        editBBPanel.add(txtMessage, editBBConstraints);
        editBBConstraints.gridy = 3;
        editBBPanel.add(txtImage, editBBConstraints);
        editBBConstraints.gridy = 4;
        editBBPanel.add(txtInformation, editBBConstraints);
        editBBConstraints.gridy = 5;
        editBBPanel.add(txtInformationColour, editBBConstraints);

        //add button to panel
        editBBConstraints.gridwidth = 2;
        editBBConstraints.insets = new Insets(5, 10, 5, 10);
        editBBConstraints.anchor = GridBagConstraints.EAST;
        editBBConstraints.gridx = 0;
        editBBConstraints.gridy = 8;
        editBBPanel.add(btnSubmit, editBBConstraints);

        getContentPane().add(editBBPanel);
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
     * @author Law
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
     * @author Law
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
     * @author Law
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }
}