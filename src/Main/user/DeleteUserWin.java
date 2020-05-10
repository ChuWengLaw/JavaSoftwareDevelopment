package Main.user;

import Main.Main;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class DeleteUserWin extends JFrame{
    private JLabel usernamelabel = new JLabel("Username:");
    private JTextField usernamefield = new JTextField(20);
    private JButton deletebutton = new JButton("Delete");
    private JPanel deletepanel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public DeleteUserWin(User user){
        super("Delete a User");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ActionListener listener = e -> {
            try{
                if(user.getUserName().equals(usernamefield.getText())){
                    JOptionPane.showMessageDialog(null, "You can't delete yourself, you knobhead");
                } else if(!CheckUserSQL(usernamefield.getText())){
                    JOptionPane.showMessageDialog(null, "Username does not exist");
                }
                else if(usernamefield.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username field is empty");
                }
                else{
                    DeleteUserSQL(usernamefield.getText());
                    JOptionPane.showMessageDialog(null,"User has been deleted");

                }
            }
            catch (SQLException ex){
                ex.printStackTrace();
            }
        };

        deletebutton.addActionListener(listener);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        deletepanel.add(usernamelabel, constraints);

        constraints.gridx = 1;
        deletepanel.add(usernamefield, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        deletepanel.add(deletebutton, constraints);

        getContentPane().add(deletepanel);

        setLocation(900,350);
        pack();
        setVisible(true);
    }
    private boolean CheckUserSQL(String userName) throws SQLException {
        User user = new User();
        boolean existing = false;
        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userName FROM user");
        while(resultSet.next()){
            if(userName.equals(resultSet.getString(1))){
                existing = true;
                break;
            }
        }
        statement.close();
        return existing;

    }
    private void DeleteUserSQL(String userName) throws SQLException {
        if(userName != usernamefield.getText()){
            PreparedStatement deletestatement = Main.connection.prepareStatement("delete from user where userName=?");
            deletestatement.setString(1,userName);
            deletestatement.executeQuery();
            deletestatement.close();
        }
        else{
            JOptionPane.showMessageDialog(null,"why");
        }
    }

}