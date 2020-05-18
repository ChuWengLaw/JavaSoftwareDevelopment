package Main;

import Main.user.*;
import Server.User;

import javax.swing.*;


public class Main {
    public static User user = new User();

    // Setting up windows
    public static MenuWin menuWin = new MenuWin();
    public static UserManagementWin userManagementWin = new UserManagementWin();
    public static ChangePasswordWin changePasswordWin = new ChangePasswordWin();
    public static CreateUserWin createUserWin = new CreateUserWin();
    public static EditUserWin editUserWin = new EditUserWin();
    public static DeleteUserWin deleteUserWin = new DeleteUserWin();
    public static ListUserWin listUserWin = new ListUserWin();

    public static void main(String[] args) {
        // User the commented code to start a new connection to server
//        Properties props = new Properties();
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream("./network.props");
//            props.load(in);
//            in.close();
//
//            // specify the socket port
//            int port = Integer.parseInt(props.getProperty("port"));
//            String address = props.getProperty("address");
//            Socket socket = new Socket(address, port);
//
//            System.out.println("Connected to Server");
//
//
//            socket.close();
//        } catch (Exception e) {
//            System.err.println(e);
//        }
        SwingUtilities.invokeLater(new LoginWin());
    }
}

