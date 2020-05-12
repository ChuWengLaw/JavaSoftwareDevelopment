package Main.billboard;

import Main.Main;
import Main.billboard.Billboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.SQLException;


public class EditBillboardGUI extends JFrame {
    //set the width of the GUI
    public static final int WIDTH = 350;
    public static final int HEIGHT = 400;

    //define element to be used
    private JButton btnSubmit;
    //private JButton btnSearch;

    //define the labels
    private JLabel lblBillboardName;
    private JLabel lblAuthor;
    private JLabel lblTextColour;
    private JLabel lblBackgroundColour;
    private JLabel lblMessage;
    private JLabel lblImage;
    private JLabel lblInformation;
    //private JPanel 

    //define the text boxes
    private JTextField txtBillboardName;
    private JTextField txtAuthor;
    private JTextField txtTextColour;
    private JTextField txtBackgroundColour;
    private JTextField txtMessage;
    private JTextField txtImage;
    private JTextField txtInformation;

    //define the strings to be used in the SQL
    private String strBillboardName;
    private String strAuthor;
    private String strTextColour;
    private String strBackgroundColour;
    private String strMessage;
    private String strImage;
    private String strInformation;


    //constructor
    public EditBillboardGUI() throws HeadlessException {
        super("Edit Billboard");
        createGUI();
    }

    /**
     * Create the base GUI to be used to create and edit the data
     *
     * @author Lachlan
     */
    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and define what text it will contain
        btnSubmit = createButton("Submit");

        //create and actionListener for the submit button
        btnSubmit.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {
                Billboard bb = new Billboard();
                strBillboardName = txtBillboardName.getText();
                strAuthor = txtAuthor.getText();
                strTextColour = txtTextColour.getText();
                strBackgroundColour = txtBackgroundColour.getText();
                strMessage = txtMessage.getText();
                strImage = txtImage.getText();
                strInformation = txtInformation.getText();
                try {
                    bb.EditBillboard(strBillboardName, strAuthor, strTextColour, strBackgroundColour, strMessage, strImage, strInformation);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //clear the textFeilds once the SQL code has been executed
                txtBillboardName.setText("");
                txtAuthor.setText("");
                txtTextColour.setText("");
                txtBackgroundColour.setText("");
                txtMessage.setText("");
                txtImage.setText("");
                txtInformation.setText("");
            }
        });

//        btnSearch = createButton("Search");
//
//        btnSearch.addActionListener(new ActionListener() {
//            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
//            @Override
//            public void actionPerformed(ActionEvent e) {}
//        });
//        });


        //create the labels
        lblBillboardName = createLabel("Billboard Name:");
        lblAuthor = createLabel("Author:");
        lblTextColour = createLabel("Text Colour:");
        lblBackgroundColour = createLabel("Background Colour:");
        lblMessage = createLabel("Message:");
        lblImage = createLabel("Image");
        lblInformation = createLabel("Information:");

        //create the text boxes to receive the data
        txtBillboardName = createText();
        txtAuthor = createText();
        txtTextColour = createText();
        txtBackgroundColour = createText();
        txtMessage = createText();
        txtImage = createText();
        txtInformation = createText();

        //create a grid layout to hold the labels and text inputs
        JPanel inputs = new JPanel(new GridLayout(7, 2));


        inputs.add(lblBillboardName);
        inputs.add(txtBillboardName);
        inputs.add(lblAuthor);
        inputs.add(txtAuthor);
        inputs.add(lblTextColour);
        inputs.add(txtTextColour);
        inputs.add(lblBackgroundColour);
        inputs.add(txtBackgroundColour);
        inputs.add(lblMessage);
        inputs.add(txtMessage);
        inputs.add(lblImage);
        inputs.add(txtImage);
        inputs.add(lblInformation);
        inputs.add(txtInformation);


        //define location of elements
        getContentPane().add(inputs);
        getContentPane().add(btnSubmit, BorderLayout.SOUTH);
        //getContentPane().add(btnSearch, BorderLayout.SOUTH);

        //set the location of the GUI
        setLocation(900,350);

        //make changes and then send to GUI
        repaint();
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
        JTextField textBox = new JTextField();
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
}