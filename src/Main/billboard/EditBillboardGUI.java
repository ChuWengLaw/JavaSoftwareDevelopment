package Main.billboard;

import Main.Main;
import Main.billboard.Billboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EditBillboardGUI extends JFrame {
    //define element to be used
    private JButton btnSubmit;
    private JButton btnSearch;

    //define the labels
    private JLabel lblBillboardName;
    private JLabel lblAuthor;
    private JLabel lblTextColour;
    private JLabel lblBackgroundColour;
    private JLabel lblMessage;
    private JLabel lblImage;
    private JLabel lblInformation;

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

    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();


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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

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

        txtAuthor.setEditable(false);
        txtTextColour.setEditable(false);
        txtBackgroundColour.setEditable(false);
        txtMessage.setEditable(false);
        txtImage.setEditable(false);
        txtInformation.setEditable(false);



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

                txtBillboardName.setEditable(true);
                txtAuthor.setEditable(false);
                txtTextColour.setEditable(false);
                txtBackgroundColour.setEditable(false);
                txtMessage.setEditable(false);
                txtImage.setEditable(false);
                txtInformation.setEditable(false);
                btnSearch.setEnabled(true);
            }
        });




        btnSearch = createButton("Search");
//################################################################################################
        btnSearch.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (!BillboardCheckSQL(txtBillboardName.getText())){
                        JOptionPane.showMessageDialog(null,"User name does not exists.");
                    }
                    else{
                        setBillboardSQL();
                        txtBillboardName.setEditable(false);
                        txtAuthor.setEditable(true);
                        txtTextColour.setEditable(true);
                        txtBackgroundColour.setEditable(true);
                        txtMessage.setEditable(true);
                        txtImage.setEditable(true);
                        txtInformation.setEditable(true);
                        btnSearch.setEnabled(false);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
//###############################################################################################









        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets=new Insets(10,10,10,10);

        //add labels to panel
        constraints.gridx=0;
        constraints.gridy=0;
        panel.add(lblBillboardName,constraints);
        constraints.gridy=1;
        panel.add(lblTextColour,constraints);
        constraints.gridy=2;
        panel.add(lblBackgroundColour,constraints);
        constraints.gridy=3;
        panel.add(lblMessage,constraints);
        constraints.gridy=4;
        panel.add(lblImage,constraints);
        constraints.gridy=5;
        panel.add(lblInformation,constraints);

        //add txtfeilds to panel
        constraints.gridx =1;
        constraints.gridy=0;
        panel.add(txtBillboardName,constraints);
        constraints.gridy=1;
        panel.add(txtTextColour,constraints);
        constraints.gridy=2;
        panel.add(txtBackgroundColour,constraints);
        constraints.gridy=3;
        panel.add(txtMessage,constraints);
        constraints.gridy=4;
        panel.add(txtImage,constraints);
        constraints.gridy=5;
        panel.add(txtInformation,constraints);

        //add button to panel
        constraints.gridwidth =1;
        constraints.insets = new Insets(5,10,5,10);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx=0;
        constraints.gridy=7;
        panel.add(btnSubmit,constraints);
        constraints.gridx=1;
        panel.add(btnSearch,constraints);

        getContentPane().add(panel);
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

    private void setBillboardSQL() throws SQLException {
        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  billboard");

        while(resultSet.next()) {
            if (txtBillboardName.getText().equals(resultSet.getString(1))){
                txtAuthor.setText(resultSet.getString(2));
                txtTextColour.setText(resultSet.getString(3));
                txtBackgroundColour.setText(resultSet.getString(4));
                txtMessage.setText(resultSet.getString(5));
                txtImage.setText(resultSet.getString(5));
                txtInformation.setText(resultSet.getString(5));
                break;
            }
        }

        statement.close();
    }




    private boolean BillboardCheckSQL(String BillboardName) throws SQLException {
        boolean existing = false;

        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT BillboardName FROM  billboard");

        while(resultSet.next()){
            if (BillboardName.equals(resultSet.getString(1))){
                existing = true;
                break;
            }
        }

        statement.close();
        return existing;
    }

}