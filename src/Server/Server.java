package Server;

import ControlPanel.User;
import Server.Request.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

import static Server.UserSQL.*;

public class Server {
    public static Connection connection;
    public static Statement statement;

    /**
     * SQL String to create a table named billboard in the database
     *
     * @author Law
     */
    private static final String DELETE_ALL =
            "DROP table billboard;" +
                    "DROP table user;" +
                    "DROP table Schedule;";

    private static final String CREATE_BILLBOARD_TABLE =
            "CREATE TABLE IF NOT EXISTS Billboard ("
                    + "BillboardName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "UserName VARCHAR(30),"
                    + "TextColour VARCHAR(30),"
                    + "BackGroundColour VARCHAR(30),"
                    + "Message VARCHAR(30),"
                    + "Image VARCHAR(30),"
                    + "Information VARCHAR(30),"
                    + "InfoColour VARCHAR(30)"+ ");";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS User ("
                    + "UserName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "UserPassword VARCHAR(64) NOT NULL,"
                    + "CreateBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditAllBillboardPermission BOOLEAN NOT NULL,"
                    + "ScheduleBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditUsersPermission BOOLEAN NOT NULL,"
                    + "SaltValue VARCHAR(64) NOT NULL" + ");";

    private static final String CREATE_SCHEDULE_TABLE =
            "CREATE TABLE IF NOT EXISTS Schedule ("
                    + "BillboardName VARCHAR(30) NOT NULL,"
                    + "ScheduleTime DATETIME NOT NULL,"
                    + "Duration INT NOT NULL,"
                    + "RecurType INT NOT NULL,"
                    + "RecurDuration INT NOT NULL" + ");"; //only required for minutes

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        /* Initiate database connection */
        initDBConnection();

        /* Setup socket connection */
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./network.props");
            props.load(in);
            in.close();

            // specify the socket port
            int port = Integer.parseInt(props.getProperty("port"));
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println(e);
        }

        for (;;) {
            Socket socket = serverSocket.accept();
            System.out.println("Received connection from " + socket.getInetAddress());

            // Stream
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            Object o = ois.readObject();

            // Handle request
            requestExecute(o, oos);
            oos.close();
            ois.close();
            socket.close();
        }
    }

    private static void initDBConnection(){
        connection = DBConnection.newConnection();
        try {
            statement = connection.createStatement();
            statement.execute(CREATE_BILLBOARD_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_SCHEDULE_TABLE);

//################code below is just to create a test user with no name or password for testing

            // Username and Password are added.
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                String testAdmin = "admin";
                String testPassword = "test1";
                String testSaltString = saltString();
                Boolean AdminExists = false;
                String hashedPassword = hashAString(testPassword + testSaltString);

                while (resultSet.next()) {
                    if (testAdmin.equals(resultSet.getString("userName"))) {
                        AdminExists = true;
                    }
                }
                if (!AdminExists) {
                    PreparedStatement pstatement = connection.prepareStatement("INSERT INTO user  " +
                            "(userName, userPassword,  createBillboardsPermission, editAllBillboardPermission, scheduleBillboardsPermission, editUsersPermission, SaltValue) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
                    pstatement.setString(1, testAdmin);
                    pstatement.setString(2, hashedPassword);
                    pstatement.setBoolean(3, true);
                    pstatement.setBoolean(4, true);
                    pstatement.setBoolean(5, true);
                    pstatement.setBoolean(6, true);
                    pstatement.setString(7, testSaltString);
                    pstatement.executeUpdate();
                    pstatement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
//#########################above code is just to create a test user with no name or password for testing
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method for hashing and salting.
    public static String hashAString(String hashString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(hashString.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash){
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    public static String saltString(){
        Random rng = new Random();
        byte[] saltBytes = new byte[32];
        rng.nextBytes(saltBytes);
        StringBuffer sb = new StringBuffer();

        for (byte b : saltBytes){
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    // Server request method
    private static void requestExecute(Object o, ObjectOutputStream oos) throws SQLException, NoSuchAlgorithmException, IOException {
        if (o instanceof LoginRequest){
            LoginRequest loginRequest = (LoginRequest) o;
            boolean loginState = checkPasswordSQL(loginRequest.getUserName(), loginRequest.getPassword());

            if (loginState){
                User user = new User();
                setUserSQL(user, loginRequest.getUserName());
                LoginReply loginReply = new LoginReply(loginState, "1827439182731", user);
                System.out.println(user.getCreateBillboardsPermission());
                oos.writeObject(loginReply);
                oos.flush();
            }
            else{
                LoginReply loginReply = new LoginReply(loginState, null);
                oos.writeObject(loginReply);
                oos.flush();
            }
        }
        else if (o instanceof CreateUserRequest) {
            CreateUserRequest createUserRequest = (CreateUserRequest) o;
            boolean createState = !checkUserSQL(createUserRequest.getUserName());

            if (createState) {
                String saltString = saltString();
                String hasedPassword = hashAString(createUserRequest.getUserPassword() + saltString);
                createUserSQL(createUserRequest.getUserName(), hasedPassword, createUserRequest.isCreateBillboardsPermission(),
                        createUserRequest.isEditAllBillboardPermission(), createUserRequest.isScheduleBillboardsPermission(),
                        createUserRequest.isEditUsersPermission(), saltString);
                GernalReply gernalReply = new GernalReply(createState);
                oos.writeObject(gernalReply);
                oos.flush();
            } else {
                GernalReply gernalReply = new GernalReply(createState);
                oos.writeObject(gernalReply);
                oos.flush();
            }
        }
        else if (o instanceof SearchRequest){
            SearchRequest searchRequest = (SearchRequest) o;
            boolean searchState = checkUserSQL(searchRequest.getUserName());

            if(searchState){
                User user = new User();
                setUserSQL(user, searchRequest.getUserName());
                SearchReply searchReply = new SearchReply(searchState, user);
                oos.writeObject(searchReply);
                oos.flush();
            }
            else{
                SearchReply searchReply = new SearchReply(searchState);
                oos.writeObject(searchReply);
                oos.flush();
            }
        }
        else if (o instanceof EditUserRequest){
            EditUserRequest editUserRequest = (EditUserRequest)o;
            boolean havePassword = editUserRequest.isHavePassword();
            boolean editState = true;

            if(havePassword){
                String saltString = saltString();
                String hasedPassword = hashAString(editUserRequest.getUserPassword() + saltString);
                editUserSQL(editUserRequest.getUserName(), hasedPassword, editUserRequest.isCreateBillboardsPermission(), editUserRequest.isEditAllBillboardPermission(),
                editUserRequest.isScheduleBillboardsPermission(), editUserRequest.isEditUsersPermission(), saltString);
                GernalReply gernalReply = new GernalReply(editState);
                oos.writeObject(gernalReply);
                oos.flush();
            }
            else{
                editUserSQL(editUserRequest.getUserName(), editUserRequest.isCreateBillboardsPermission(), editUserRequest.isEditAllBillboardPermission(),
                        editUserRequest.isScheduleBillboardsPermission(), editUserRequest.isEditUsersPermission());
                GernalReply gernalReply = new GernalReply(editState);
                oos.writeObject(gernalReply);
                oos.flush();
            }
        }
        else if(o instanceof DeleteUserRequest){
            DeleteUserRequest deleteUser = (DeleteUserRequest) o;
            boolean checkDeleteUser = checkUserSQL(deleteUser.getUserName());
            if (checkDeleteUser){
                deleteUserBillboardSQL(deleteUser.getUserName());
                GernalReply generalReply = new GernalReply(true);
                oos.writeObject(generalReply);
                oos.flush();
            }
            else{
                GernalReply generalReply = new GernalReply(false);
                oos.writeObject(generalReply);
                oos.flush();
            }
        }
        else if (o instanceof ListUserRequest){
            ListUserRequest listUser = (ListUserRequest) o;
            boolean validSession = false;
            System.out.println(validSession);
            //Insert code here to check if session is valid
            //Default value to true for now
            if (validSession){
                ListUserReply listUserReply = new ListUserReply(listUserSQL(), validSession);
                oos.writeObject(listUserReply);
                oos.flush();
            }
            else{
                System.out.println(validSession);
                ListUserReply listUserReply = new ListUserReply(listUserSQL(), validSession);
                oos.writeObject(listUserReply);
                oos.flush();
            }

        }
        else if (o instanceof CreateBBRequest) {
            CreateBBRequest temp = (CreateBBRequest) o;
            BillboardSQL bb = new BillboardSQL();
            bb.CreateBillboard(temp.getBillboardName(), temp.getAuthor(), temp.getTextColour(), temp.getBackgroundColour(),
                    temp.getMessage(), temp.getImage(), temp.getInformation(), temp.getInformationColour());
            oos.flush();
        }
        else if (o instanceof DeleteBBRequest) {
            DeleteBBRequest temp = (DeleteBBRequest) o;
            BillboardSQL bb = new BillboardSQL();
            bb.DeleteBillboard(temp.getBillboardName());
            oos.flush();
        }
        else if (o instanceof BBInfoRequest) {
            BBInfoRequest temp = (BBInfoRequest) o;
            BillboardSQL bb = new BillboardSQL();
            String info = bb.GetBillboardInfo(temp.getBillboardName());
            BBInfoReply bbInfoReply = new BBInfoReply(info);
            oos.writeObject(bbInfoReply);
            oos.flush();
        }
        else if (o instanceof ListBBRequest) {
            ListBBRequest listBBRequest = (ListBBRequest) o;
            BillboardSQL bb = new BillboardSQL();
            ListBBReply listBBReply = new ListBBReply(bb.ListBillboards(listBBRequest.getSessionToken()));
            oos.writeObject(listBBReply);
        }
        else if (o instanceof ScheduleBillboardRequest) {
            ScheduleBillboardRequest temp = (ScheduleBillboardRequest) o;
            ScheduleSQL Schedule = new ScheduleSQL();
            Schedule.ScheduleBillboard(temp.getBillboardName(),temp.getScheduledTime(), temp.getDuration(),temp.getReoccurType(),temp.getReoccurAmount());
            oos.flush();
        }
    }
}
