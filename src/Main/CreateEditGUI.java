package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.SQLException;

public class CreateEditGUI extends JFrame implements ActionListener,Runnable{
    //set the width of the GUI
    public static final int WIDTH = 350;
    public static final int HEIGHT = 400;

    //define element to be used
    private JButton btnSubmit;

    private JLabel lblBillboardName;
    private JLabel lblAuthor;
    private JLabel lblTextColour;
    private JLabel lblBackgroundColour;
    private JLabel lblMessage;
    private JLabel lblImage;
    private JLabel lblInformation;

    private JTextField txtBillboardName;
    private JTextField txtAuthor;
    private JTextField txtTextColour;
    private JTextField txtBackgroundColour;
    private JTextField txtMessage;
    private JTextField txtImage;
    private JTextField txtInformation;

    private String strBillboardName;
    private String strAuthor;
    private String strTextColour;
    private String strBackgroundColour;
    private String strMessage;
    private String strImage;
    private String strInformation;

    public CreateEditGUI(String title) throws HeadlessException {
        super(title);
    }

    /**
     * Create the base GUI to be used to create and edit the data
     * @author Lachlan
     */
    private void createGUI(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and define what text it will contain
        btnSubmit=createButton("Submit");
        //create and actionListner for the sumbit button
        btnSubmit.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {
                Billboard bb = new Billboard();
                strBillboardName = txtBillboardName.getText();
                strAuthor=txtAuthor.getText();
                strTextColour = txtTextColour.getText();
                strBackgroundColour=txtBackgroundColour.getText();
                strMessage = txtMessage.getText();
                strImage = txtImage.getText();
                strInformation = txtInformation.getText();
                try {
                    bb.CreateEditBillboard(strBillboardName,strAuthor, strTextColour, strBackgroundColour,strMessage,strImage, strInformation);
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

        //create the labels
        lblBillboardName = createLabel("Billboard Name:");
        lblAuthor = createLabel("Author:");
        lblTextColour = createLabel("Text Colour:");
        lblBackgroundColour = createLabel("Background Colour:");
        lblMessage = createLabel("Message:");
        lblImage = createLabel("Image");
        lblInformation = createLabel("Information:");

        //create the text boxes to receive the data
        txtBillboardName= createText();
        txtAuthor = createText();
        txtTextColour =createText();
        txtBackgroundColour = createText();
        txtMessage=createText();
        txtImage=createText();
        txtInformation=createText();

        //create a grid layout to hold the labels and text inputs
        JPanel inputs = new JPanel(new GridLayout(7,2));
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
        getContentPane().add(btnSubmit,BorderLayout.SOUTH);

        //make changes and then send to GUI
        repaint();
        setVisible(true);
    }

    /**
     * This function creates a JButton
     * @author Lachlan
     * @param text the text with will be on the button
     * @return a JButton with text on it
     */
    private JButton createButton(String text){
        JButton button = new JButton();
        button.setText(text);
        return button;
    }

    /**
     * This function create a blank JTextField
     * @author Lachlan
     * @return an empty JTextField
     */
    private JTextField createText() {
        JTextField textBox = new JTextField();
        return textBox;
    }

    /**
     * This function create a JTextField with the input of the text
     * @author Lachlan
     * @param text the text to be displayed
     * @return a JTextField with the input of text
     */
    private JTextField createText(String text){
        JTextField textBox = new JTextField();
        textBox.setText(text);
        strBillboardName = text;
        return textBox;
    }

    /**
     * This function create a JLabel with the title of the text
     * @author Lachlan
     * @param text the title of the label
     * @return a JLabel with the title of text
     */
    private JLabel createLabel (String text){
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
        createGUI();
    }
}