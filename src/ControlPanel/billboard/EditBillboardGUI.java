package ControlPanel.billboard;

import ControlPanel.Client;
import Server.Request.CreateBBRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditBillboardGUI extends JFrame {

    //define the buttons
    private JButton btnSumbit;
    private JButton btnSearch;

    //define the Labels
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

    //define the panel and gridbag to be used for the GUI layout
    private JPanel editBBPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints editBBConstraints = new GridBagConstraints();

    public EditBillboardGUI() throws HeadlessException {
        super("Create Billboard");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //create the buttons
        btnSumbit = createButton("Submit");
        btnSearch = createButton("Search");

        //if the submit button is click perform the following
        btnSumbit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the billboard field is blank display appropriate message
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please Enter a Billboard Name.");
                }
                //if the name contains a space display an appropriate message
                else if (txtBillboardName.getText().contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please Enter the Name as One Word");
                    //otherwise edit the billboard
                } else {
                    CreateBBRequest temp = new CreateBBRequest(txtBillboardName.getText(), txtTextColour.getText(),
                            txtBackgroundColour.getText(), txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText());

                    try {
                        Client.connectServer(temp);
                        new MakeXMLFile(txtBillboardName.getText(), txtTextColour.getText(),
                                txtBackgroundColour.getText(), txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //if the search button is clicked do the following
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (txtBillboardName.getText().isBlank()){
                JOptionPane.showMessageDialog(null, "Please Enter a Billboard Name.");
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

        //create the textboxes
        txtBillboardName = createText();
        txtTextColour = createText();
        txtBackgroundColour = createText();
        txtMessage = createText();
        txtImage = createText();
        txtInformation = createText();
        txtInformationColour = createText();

        //disable all feilds other than billboard name
        txtTextColour.setEditable(false);
        txtBackgroundColour.setEditable(false);
        txtMessage.setEditable(false);
        txtImage.setEditable(false);
        txtInformation.setEditable(false);
        txtInformationColour.setEditable(false);

        editBBConstraints.anchor = GridBagConstraints.WEST;
        editBBConstraints.insets = new Insets(10, 10, 10, 10);

        //add labels to panel
        editBBConstraints.gridx = 0;
        editBBConstraints.gridy = 0;
        editBBPanel.add(lblBillboardName, editBBConstraints);
        editBBConstraints.gridy = 1;
        editBBPanel.add(lblTextColour, editBBConstraints);
        editBBConstraints.gridy = 2;
        editBBPanel.add(lblBackgroundColour, editBBConstraints);
        editBBConstraints.gridy = 3;
        editBBPanel.add(lblMessage, editBBConstraints);
        editBBConstraints.gridy = 4;
        editBBPanel.add(lblImage, editBBConstraints);
        editBBConstraints.gridy = 5;
        editBBPanel.add(lblInformation, editBBConstraints);
        editBBConstraints.gridy = 6;
        editBBPanel.add(lblInformationColour, editBBConstraints);

        //add txtfeilds to panel
        editBBConstraints.gridx = 1;
        editBBConstraints.gridy = 0;
        editBBPanel.add(txtBillboardName, editBBConstraints);
        editBBConstraints.gridy = 1;
        editBBPanel.add(txtTextColour, editBBConstraints);
        editBBConstraints.gridy = 2;
        editBBPanel.add(txtBackgroundColour, editBBConstraints);
        editBBConstraints.gridy = 3;
        editBBPanel.add(txtMessage, editBBConstraints);
        editBBConstraints.gridy = 4;
        editBBPanel.add(txtImage, editBBConstraints);
        editBBConstraints.gridy = 5;
        editBBPanel.add(txtInformation, editBBConstraints);
        editBBConstraints.gridy = 6;
        editBBPanel.add(txtInformationColour, editBBConstraints);

        //add buttons to panel
        editBBConstraints.insets = new Insets(5, 10, 5, 10);
        editBBConstraints.anchor = GridBagConstraints.EAST;
        editBBConstraints.gridx = 0;
        editBBConstraints.gridy = 8;
        editBBPanel.add(btnSumbit, editBBConstraints);
        editBBConstraints.gridx = 1;
        editBBPanel.add(btnSearch, editBBConstraints);


        getContentPane().add(editBBPanel);
        setLocation(900, 350);

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

}