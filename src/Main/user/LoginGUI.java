package Main.user;

import Main.Main;
import Main.billboard.CreateEditGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI extends JFrame implements Runnable{

    public static int WIDTH = 400;
    public static int HEIGHT = 300;


    private JPanel GUIpanel;
    private JButton BtnLogin;
    private JTextField User_Submitted;
    private JPasswordField Password_Submitted;
    private JLabel User_Label, Password_Label;
    private JLabel TestMessage;

    public LoginGUI(String title) throws HeadlessException {
        super(title);
    }

    private void createGUI()
    {
        // User Label
        User_Label = new JLabel();
        User_Label.setText("User:");
        User_Submitted = new JTextField();

        // Password

        Password_Label = new JLabel();
        Password_Label.setText("Password:");
        Password_Submitted = new JPasswordField();

        BtnLogin = new JButton("Login");

        GUIpanel = new JPanel(new GridLayout(3, 1));
        TestMessage = new JLabel();


        GUIpanel.add(User_Label);
        GUIpanel.add(User_Submitted);
        GUIpanel.add(Password_Label);
        GUIpanel.add(Password_Submitted);
        GUIpanel.add(BtnLogin);
        GUIpanel.add(TestMessage);




        add(GUIpanel, BorderLayout.CENTER);

        setTitle("Login");
        setVisible(true);
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BtnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUserName = User_Submitted.getText();
                String enteredPassword = Password_Submitted.getText();


                /*
                get all users and passwords from sql and loop through them to find the matches
                 */

                String DatabaseUserName = "";
                String DatabasePassword = "";

                try {
                    ResultSet resultSet = Main.statement.executeQuery("SELECT * FROM User");
                    while (resultSet.next()) {
                        if ( enteredUserName.equals(resultSet.getString("UserName")) && enteredPassword.equals(resultSet.getString("UserPassword"))) {
                           TestMessage.setText(" Hello " + enteredUserName
                            + "");
                            //SwingUtilities.invokeLater(new CreateEditGUI("Create Edit"));
                        }
                        else {
                            TestMessage.setText(" Invalid user.. ");
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }

            }
        });

    }


    @Override
    public void run() {
        createGUI();
    }
}
