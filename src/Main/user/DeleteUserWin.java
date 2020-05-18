package Main.user;

import Main.Main;
import Server.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.*;

public class DeleteUserWin extends JFrame{
    private JLabel usernamelabel = new JLabel("Username:");
    private JTextField usernamefield = new JTextField(20);
    private JButton deletebutton = new JButton("Delete");
    private JPanel deletepanel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public DeleteUserWin(){
        super("Delete a User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                Main.userManagementWin.setEnabled(true);
                Main.userManagementWin.setVisible(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };
        super.addWindowListener(windowListener);

        ActionListener listener = e -> {
            try{
                if(Main.user.getUserName().equals(usernamefield.getText())){
                    JOptionPane.showMessageDialog(null, "You can't delete yourself, you knobhead");
                } else if(!CheckUserSQL(usernamefield.getText())){
                    JOptionPane.showMessageDialog(null, "Username does not exist");
                }
                else if(usernamefield.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username field is empty");
                }
                else{
                    DeleteUserSQL(usernamefield.getText());
                    DeleteUserBillboardSQL(usernamefield.getText());
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
    }
}