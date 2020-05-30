package ControlPanel.user;

import ControlPanel.*;
import Server.Request.DeleteUserRequest;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;
import javax.swing.*;
/**
 * This class creates the GUI which allows the user
 * to delete the entered user from the database
 *
 * @author "Kenji" Foo Shiang Xun
 */
public class DeleteUserWin extends JFrame{
    private JLabel usernamelabel = new JLabel("Username:");
    private JTextField usernamefield = new JTextField(20);
    private JButton deletebutton = new JButton("Delete");
    private JPanel deletepanel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation as well as set the
     * window listener.
     */
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
                usernamefield.setText("");
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
        //Create and adding actionlistener to the delete button
        ActionListener listener = e -> {
            if (usernamefield.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty");
            }
            else if (usernamefield.getText().equals(Main.loginUser.getUserName()))
            {
                JOptionPane.showMessageDialog(null, "Hey you, Stop that. You cannot delete your own account");
            }
            else{
                //Sending the delete user request to the server to delete the entered user from the database
                DeleteUserRequest deleteUser = new DeleteUserRequest(usernamefield.getText(), Main.loginUser.getSessionToken());

                try{
                    Client.connectServer(deleteUser);

                    if (Client.isRequestState()){
                        JOptionPane.showMessageDialog(null, "User has been deleted");
                    }
                    else{
                        throw new Exception();
                    }
                } catch(ConnectException ex){
                    JOptionPane.showMessageDialog(null, "Connection fail.");
                    System.exit(0);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "User does not exist");
                }

                usernamefield.setText("");
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);
        pack();
    }
}