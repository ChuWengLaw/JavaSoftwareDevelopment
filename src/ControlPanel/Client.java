package ControlPanel;

import Server.Request.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Client {
    private static boolean requestState;

    public static void connectServer(Object args) throws IOException, InterruptedException, ClassNotFoundException {
        // Set up socket.
        Properties props = new Properties();
        FileInputStream in = null;
        in = new FileInputStream("./network.props");
        props.load(in);
        in.close();
        String host = props.getProperty("address");
        int port = Integer.parseInt(props.getProperty("port"));
        Socket socket = new Socket(host, port);

        // Set up input and output stream.
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        oos.writeObject(args);
        oos.flush();

        Object requestReply = ois.readObject();
        executeReply(requestReply);

        ois.close();
        oos.close();
    }

    public static boolean isRequestState() {
        return requestState;
    }

    private static void executeReply(Object requestReply){
        if (requestReply instanceof LoginReply){
            LoginReply loginReply = (LoginReply) requestReply;
            requestState = loginReply.isLoginState();

            if (requestState){
                Main.loginUser = loginReply.getUser();
                Main.loginUser.setSessionToken(loginReply.getSessionToken());
            }
        }
        else if (requestReply instanceof SearchReply){
            SearchReply searchReply = (SearchReply) requestReply;
            requestState = searchReply.isRequestState();

            if (requestState){
                Main.editUserWin.editedUser = searchReply.getUser();
            }
        }
        else if (requestReply instanceof LogoutReply){
            LogoutReply logoutReply = (LogoutReply) requestReply;

            // Display different message upon different logout reason.
            if(logoutReply.isExpired()){
                JOptionPane.showMessageDialog(null, "Session expired, system logged out.");
            }
            else{
                JOptionPane.showMessageDialog(null, "Successfully logged out!");
            }
            System.exit(0);
        }
        else if (requestReply instanceof GeneralReply){
            GeneralReply generalReply = (GeneralReply) requestReply;
            requestState = generalReply.isRequestState();
        }
    }
}
