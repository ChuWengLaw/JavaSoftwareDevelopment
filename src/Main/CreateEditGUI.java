package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class CreateEditGUI extends JFrame implements ActionListener,Runnable{
    //set the width of the GUI
    public static final int WIDTH = 450;
    public static final int HEIGHT = 600;

    public CreateEditGUI(String title) throws HeadlessException {
        super(title);
    }

    //define element to be used
    private JPanel pnlInputs;

    private JButton btnSubmit;

    private JLabel lblBillboardName;
    private JLabel lblTextColour;
    private JLabel lblBackgroundColour;
    private JLabel lblMessage;
    private JLabel lblImage;
    private JLabel lblInformation;

    private JTextField txtBillboardName;
    private JTextField txtTextColour;
    private JTextField txtBackgroundColour;
    private JTextField txtMessage;
    private JTextField txtImage;
    private JTextField txtInformation;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CreateEditGUI("Create and Edit"));
    }

    /**
     * Create the base GUI to be used to create and edit the data
     * @author Lachlan
     */
    private void createGUI(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //define the colour of elements
        pnlInputs = createPanel(Color.lightGray);
        btnSubmit=createButton("Submit");

        //create the labels
        lblBillboardName = createLabel("Billboard Name:");
        lblTextColour = createLabel("Text Colour:");
        lblBackgroundColour = createLabel("Background Colour:");
        lblMessage = createLabel("Message:");
        lblImage = createLabel("Image");
        lblInformation = createLabel("Information:");

        //create the text boxes to receive the data
        txtBillboardName= createText();
        txtTextColour =createText();
        txtBackgroundColour = createText();
        txtMessage=createText();
        txtImage=createText();
        txtInformation=createText();

        //define location of elements
        getContentPane().add(pnlInputs,BorderLayout.CENTER);
        //getContentPane().add(btnSubmit,BorderLayout.SOUTH);
        //getContentPane().add(txtBillboardName,BorderLayout.NORTH);
        //getContentPane().add(lblBillboardName,BorderLayout.EAST);

        layoutButtonPanel();

        //make changes and then send to GUI
        repaint();
        setVisible(true);
    }

    /**
     * Creates a JPanel
     * @author Lachlan
     * @param c the colour of the JPanel
     * @return a JPanel of colour c
     */
    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
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

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlInputs.setLayout(layout);
        //Lots of layout code here
        //add components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        //Defaults
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 100;
        constraints.weighty = 100;

        addToPanel(pnlInputs, lblBillboardName,constraints,0,0,2,1);
        addToPanel(pnlInputs, txtBillboardName,constraints,3,0,2,1);
        addToPanel(pnlInputs, lblTextColour,constraints,0,2,2,1);
        addToPanel(pnlInputs, txtTextColour,constraints,3,2,2,1);
        addToPanel(pnlInputs, lblBackgroundColour, constraints,0,4,2,1);
        addToPanel(pnlInputs,txtBackgroundColour,constraints,3,4,2,1);
        addToPanel(pnlInputs,lblMessage,constraints,0,6,2,1);
        addToPanel(pnlInputs,txtMessage,constraints,3,6,2,1);
        addToPanel(pnlInputs,lblImage,constraints,0,8,2,1);
        addToPanel(pnlInputs,txtImage,constraints,3,8,2,1);
        addToPanel(pnlInputs,lblInformation,constraints,0,10,2,1);
        addToPanel(pnlInputs,txtInformation,constraints,3,10,2,1);
        addToPanel(pnlInputs,btnSubmit,constraints,2,12,2,1);
    }

    /**
     *
     * A convenience method to add a component to given grid bag
     * layout locations. Code due to Cay Horstmann
     *
     * @param c the component to add
     * @param constraints the grid bag constraints to use
     * @param x the x grid position
     * @param y the y grid position
     * @param w the grid width of the component
     * @param h the grid height of the component
     */
    private void addToPanel(JPanel jp,Component c, GridBagConstraints
            constraints,int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
        createGUI();
    }
}