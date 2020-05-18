package Main;

import Main.billboard.BillBoardManagementGUI;
import Main.user.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

public class Main {
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

