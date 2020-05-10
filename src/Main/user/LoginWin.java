package Main.user;

import Main.Main;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LoginWin extends JFrame implements Runnable{
    private JLabel labelUserID = new JLabel("User ID");
    private JLabel labelPassword= new JLabel("Password");
    private JTextField idTextField = new JTextField(20);
    private JPasswordField passwordTextField = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();
    public User user = new User();

    public LoginWin(){
        // Setting default value of the frame
        super("Login");
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button setting
        ActionListener loginListener = e ->{
            try {
                if (!CheckUserSQL(idTextField.getText())){
                    new ErrorWin("User Name does not exists.");
                }
                else if (!CheckPasswordSQL(idTextField.getText(), passwordTextField.getPassword())){
                    new ErrorWin("Wrong password.");
                }
                else{
                    super.dispose();
                    SetUserSQL(user);
                    new MeauWin(user);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };
        loginButton.addActionListener(loginListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUserID, constraints);

        constraints.gridx = 1;
        panel.add(idTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);

        constraints.gridx = 1;
        panel.add(passwordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }

    private void SetUserSQL(User user) throws SQLException {
        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  user");

        while (resultSet.next()) {
            if (idTextField.getText().equals(resultSet.getString("userName"))) {
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("userPassword"));
                user.setCreateBillboardsPermission(resultSet.getBoolean("createBillboardsPermission"));
                user.setEditAllBillboardsPermission(resultSet.getBoolean("editAllBillboardPermission"));
                user.setScheduleBillboardsPermission(resultSet.getBoolean("scheduleBillboardsPermission"));
                user.setEditUsersPermission(resultSet.getBoolean("editUsersPermission"));
                break;
            }
        }

        statement.close();
    }

    private boolean CheckUserSQL(String userName) throws SQLException {
        boolean existing = false;

        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userName FROM  user");

        while(resultSet.next()){
            if (userName.equals(resultSet.getString(1))){
                existing = true;
                break;
            }
        }

        statement.close();
        return existing;
    }

    private boolean CheckPasswordSQL(String userName, char[] userPassword) throws SQLException {
        boolean correctPassword = false;
        String userPasswordString = String.valueOf(userPassword);

        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userName, userPassword FROM  user");

        while(resultSet.next()){
            if (userName.equals(resultSet.getString(1)) && userPasswordString.equals(resultSet.getString(2))){
                correctPassword = true;
                break;
            }
        }

        statement.close();
        return correctPassword;
    }

    @Override
    public void run() {
        createGUI();
    }
}
