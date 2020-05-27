package ControlPanel;

import Server.Reply.*;
import Server.Reply.EditBBReply;
import Server.Reply.WeeklyScheduleReply;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class setups a stateless connection to the server socket whenever
 * an action is performed in other billboard/user GUI classes
 */
public class Client {
    private static boolean requestState;
    private static String info;
    private static JTable listBBTable;
    private static String EditTextColour, EditBGColour, EditMsg, EditImg, EditInfo, EditInfoColour;
    private static ArrayList<String[]> ScheduleArray;
    private static String ScheduledBillboardTitle;

    /**
     * Connects to server (connection read from network.props)
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

            Main.loginUser.setSessionToken(searchReply.getSessionToken());
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
            Main.loginUser.setSessionToken(generalReply.getSessionToken());
        }
        else if (requestReply instanceof ListUserReply){
            ListUserReply listUserReply = (ListUserReply) requestReply;
            Main.listUserWin.getTable(listUserReply.getTable());
            requestState = listUserReply.isListUserState();
            Main.loginUser.setSessionToken(listUserReply.getSessionToken());
        }
        else if (requestReply instanceof BBInfoReply){
            BBInfoReply bbInfoReply = (BBInfoReply) requestReply;
            info = bbInfoReply.getInformation();
            requestState = true;
            Main.loginUser.setSessionToken(bbInfoReply.getSessionToken());
        }
        else if (requestReply instanceof ListBBReply){
            ListBBReply listBBReply = (ListBBReply) requestReply;
            listBBTable = listBBReply.getTable();
            requestState = true;
            Main.loginUser.setSessionToken(listBBReply.getSessionToken());
        }
        else if (requestReply instanceof WeeklyScheduleReply){
            WeeklyScheduleReply ScheduleReply = (WeeklyScheduleReply) requestReply;
            ScheduleArray = ScheduleReply.getArray();
        }

        else if (requestReply instanceof GetCurrentScheduledReply){
            GetCurrentScheduledReply ScheduleReply = (GetCurrentScheduledReply) requestReply;
            ScheduledBillboardTitle = ScheduleReply.getBillboardTitle();
        }

        else if (requestReply instanceof EditBBReply){
            EditBBReply editBBReply = (EditBBReply) requestReply;
            EditBGColour = editBBReply.getEditBGColour();
            EditTextColour = editBBReply.getEditTextColour();
            EditMsg = editBBReply.getEditMsg();
            EditImg = editBBReply.getEditImg();
            EditInfo = editBBReply.getEditInfo();
            EditInfoColour = editBBReply.getEditInfoColour();
            requestState = true;
            Main.loginUser.setSessionToken(editBBReply.getSessionToken());
        }
    }

    public static String getInfo() {
        return info;
    }
    public static JTable getBBTable() {
        return listBBTable;
    }
    public static String getEditTextColour() {
        return EditTextColour;
    }
    public static String getEditBGColour() {
        return EditBGColour;
    }
    public static String getEditMsg() {
        return EditMsg;
    }
    public static String getEditImg() {
        return EditImg;
    }
    public static String getEditInfo() {
        return EditInfo;
    }
    public static String getEditInfoColour() {
        return EditInfoColour;
    }
    public static ArrayList<String[]> getScheduleArray() {return ScheduleArray;}
    public static String getScheduledBillboardTitle() {return ScheduledBillboardTitle;}
}


