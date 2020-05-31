package Viewer;

import Server.Reply.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

import java.util.ArrayList;


import java.util.Properties;

/**
 * This class setups a stateless connection to the server socket whenever
 * an action is performed in other billboard/user GUI classes
 */
public class ViewerClient {
    private static boolean requestState;
    private static String info = "";
    private static JTable listBBTable;
    private static ArrayList<String[]> ScheduleArray;
    private static String ScheduledBillboardTitle;

    /**
     * Connects to server (connection read from network.props)
     *
     * @param args The object encapsulating the data inputs to be sent to server
     */
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

    private static void executeReply(Object requestReply) {
        if (requestReply instanceof GetCurrentScheduledReply) {
            GetCurrentScheduledReply ScheduleReply = (GetCurrentScheduledReply) requestReply;
            ScheduledBillboardTitle = ScheduleReply.getBillboardTitle();
        }
    }

    public static String getInfo() {
        return info;
    }

    public static String getScheduledBillboardTitle() {
        return ScheduledBillboardTitle;
    }

}


