package ControlPanel;

import Server.Request.LoginReply;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Client {
    private static Object requestReply;
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

        if (requestReply instanceof LoginReply){
            LoginReply loginReply = (LoginReply) requestReply;
            System.out.println("successful login");
            requestState = loginReply.isLoginState();

//            if (loginReply.isLoginState()) {
//                requestState = true;
//                System.out.println(loginReply.getSessionToken());
//            }
//            else {
//                requestState = false;
//            }
        }

        ois.close();
        oos.close();



    }

    public static boolean isRequestState() {
        return requestState;
    }
}
