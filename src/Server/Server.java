package Server;

import ControlPanel.User;
import Server.Request.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static Server.UserSQL.*;

public class Server {
    public static Connection connection;
    public static Statement statement;
    private static List<SessionToken> sessionTokens = new ArrayList<SessionToken>();
    private static ServerSocket serverSocket;
    private static int expiryHour = 24;

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
            Object clientRequest = ois.readObject();

            // Handle request
            requestExecute(clientRequest, oos);
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
                String testSaltString = randomString();
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

    /**
     *
     * @param hashString
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hashAString(String hashString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(hashString.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash){
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    /**
     *
     * @return
     */
    public static String randomString(){
        Random rng = new Random();
        byte[] saltBytes = new byte[32];
        rng.nextBytes(saltBytes);
        StringBuffer sb = new StringBuffer();

        for (byte b : saltBytes){
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    /**
     *
     * @param sessionToken
     * @return
     */
    private static boolean tokenCheck(SessionToken sessionToken){
        boolean tokenAvailableState;

        // This is used for testing, so the token will expire after one minute of hanging.
        if(sessionToken.getUsedTime().plusMinutes(1).isAfter(LocalDateTime.now())){
            tokenAvailableState = true;
        }
        else{
            tokenAvailableState = false;
        }

//        if(sessionToken.getUsedTime().plusHours(expiryHour).isAfter(LocalDateTime.now())){
//            tokenAvailableState = true;
//        }
//        else{
//            tokenAvailableState = false;
//        }

        return tokenAvailableState;
    }

    /**
     *
     * @param sessionToken
     * @return
     */
    private static SessionToken findSessionToken(SessionToken sessionToken){
        for(int i  = 0; i <= sessionTokens.size(); i++){
            if(sessionTokens.get(i).getSessionTokenString().equals(sessionToken.getSessionTokenString())){
                sessionToken = sessionTokens.get(i);
                break;
            }
        }

        return sessionToken;
    }

    /**
     *
     * @param sessionToken
     */
    private static void resetSessionTokenTime(SessionToken sessionToken){
        for (int i = 0; i <= sessionTokens.size(); i++) {
            if (sessionTokens.get(i).getSessionTokenString().equals(sessionToken.getSessionTokenString())) {
                sessionTokens.get(i).setUsedTime(LocalDateTime.now());
                break;
            }
        }
    }

    /**
     *
     * @param clientRequest
     * @param oos
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void requestExecute(Object clientRequest, ObjectOutputStream oos) throws SQLException, NoSuchAlgorithmException, IOException {
        if (clientRequest instanceof LoginRequest){
            LoginRequest loginRequest = (LoginRequest) clientRequest;
            boolean loginState = checkPasswordSQL(loginRequest.getUserName(), loginRequest.getPassword());

            if (loginState){
                User user = new User();
                setUserSQL(user, loginRequest.getUserName());

                // Create a new session token and store it into the list for multiple login
                String sessionTokenString = randomString();
                SessionToken sessionToken = new SessionToken(sessionTokenString, LocalDateTime.now());
                sessionTokens.add(sessionToken);

                LoginReply loginReply = new LoginReply(true, sessionToken, user);
                oos.writeObject(loginReply);
            }
            else{
                LoginReply loginReply = new LoginReply(false, null);
                oos.writeObject(loginReply);
            }
            oos.flush();
        }
        else if (clientRequest instanceof CreateUserRequest){
            CreateUserRequest createUserRequest = (CreateUserRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(createUserRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(createUserRequest.getSessionToken())){
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else{
                boolean createState = !checkUserSQL(createUserRequest.getUserName());

                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);

                if (createState) {
                    String saltString = randomString();
                    String hashedPassword = hashAString(createUserRequest.getUserPassword() + saltString);
                    createUserSQL(createUserRequest.getUserName(), hashedPassword, createUserRequest.isCreateBillboardsPermission(),
                            createUserRequest.isEditAllBillboardPermission(), createUserRequest.isScheduleBillboardsPermission(),
                            createUserRequest.isEditUsersPermission(), saltString);

                    GeneralReply generalReply = new GeneralReply(true);
                    oos.writeObject(generalReply);
                }
                else{
                    GeneralReply generalReply = new GeneralReply(false);
                    oos.writeObject(generalReply);
                }
            }

            oos.flush();
        }
        else if (clientRequest instanceof SearchRequest) {
            SearchRequest searchRequest = (SearchRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(searchRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(searchRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else {
                boolean searchState = checkUserSQL(searchRequest.getUserName());

                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);

                // Reply based on the existence of the user name that was searched.
                if (searchState) {
                    User user = new User();
                    setUserSQL(user, searchRequest.getUserName());
                    SearchReply searchReply = new SearchReply(true, user);
                    oos.writeObject(searchReply);
                } else {
                    SearchReply searchReply = new SearchReply(false);
                    oos.writeObject(searchReply);
                }
            }

            oos.flush();
        }
        else if (clientRequest instanceof EditUserRequest){
            EditUserRequest editUserRequest = (EditUserRequest)clientRequest;
            boolean havePassword = editUserRequest.isHavePassword();
            SessionToken sessionToken = findSessionToken(editUserRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(editUserRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else{
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);

                // Edit SQL depends whether a new password is passed in, so when the password field is empty,
                // the password won't change.
                if(havePassword){
                    String saltString = randomString();
                    String hasedPassword = hashAString(editUserRequest.getUserPassword() + saltString);
                    editUserSQL(editUserRequest.getUserName(), hasedPassword, editUserRequest.isCreateBillboardsPermission(), editUserRequest.isEditAllBillboardPermission(),
                            editUserRequest.isScheduleBillboardsPermission(), editUserRequest.isEditUsersPermission(), saltString);
                    GeneralReply generalReply = new GeneralReply(true);
                    oos.writeObject(generalReply);
                }
                else{
                    editUserSQL(editUserRequest.getUserName(), editUserRequest.isCreateBillboardsPermission(), editUserRequest.isEditAllBillboardPermission(),
                            editUserRequest.isScheduleBillboardsPermission(), editUserRequest.isEditUsersPermission());
                    GeneralReply generalReply = new GeneralReply(true);
                    oos.writeObject(generalReply);
                }
            }

            oos.flush();
        }
        else if(clientRequest instanceof ChangePasswordRequest){
            ChangePasswordRequest changePasswordRequest = (ChangePasswordRequest)clientRequest;
            String saltString = randomString();
            String hashedPassword = hashAString(changePasswordRequest.getNewPassword() + saltString);
            SessionToken sessionToken = findSessionToken(changePasswordRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if(!tokenCheck(changePasswordRequest.getSessionToken())){
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else{
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);

                changePasswordSQL(changePasswordRequest.getUserName(), hashedPassword, saltString);
                GeneralReply generalReply = new GeneralReply(true);
                oos.writeObject(generalReply);
            }
            oos.flush();
        }
        else if (clientRequest instanceof  LogoutRequest){
            LogoutRequest logoutRequest = (LogoutRequest) clientRequest;

            // Remove session token from the list
            for(int i  = 0; i <= sessionTokens.size(); i++){
                if(sessionTokens.get(i).getSessionTokenString().equals(logoutRequest.getSessionToken().getSessionTokenString())){
                    sessionTokens.remove(i);
                    break;
                }
            }

            LogoutReply logoutReply = new LogoutReply(false);
            oos.writeObject(logoutReply);
            oos.flush();
        }
        else if(clientRequest instanceof DeleteUserRequest){
            DeleteUserRequest deleteUser = (DeleteUserRequest) clientRequest;
            boolean checkDeleteUser = checkUserSQL(deleteUser.getUserName());

            if (checkDeleteUser){
                deleteUserBillboardSQL(deleteUser.getUserName());
                GeneralReply generalReply = new GeneralReply(true);
                oos.writeObject(generalReply);
                oos.flush();
            }
            else{
                GeneralReply generalReply = new GeneralReply(false);
                oos.writeObject(generalReply);
                oos.flush();
            }
        }
        else if (clientRequest instanceof ListUserRequest){
            ListUserRequest listUserRequest = (ListUserRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(listUserRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if(!tokenCheck(listUserRequest.getSessionToken())){
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else{
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);

                ListUserReply listUserReply = new ListUserReply(listUserSQL(), true);
                oos.writeObject(listUserReply);
                oos.flush();
            }
        }
        else if (clientRequest instanceof CreateBBRequest) {
            CreateBBRequest temp = (CreateBBRequest) clientRequest;
            BillboardSQL bb = new BillboardSQL();
            bb.CreateBillboard(temp.getBillboardName(), temp.getAuthor(), temp.getTextColour(), temp.getBackgroundColour(),
                    temp.getMessage(), temp.getImage(), temp.getInformation(), temp.getInformationColour());
            oos.flush();
        }
        else if (clientRequest instanceof DeleteBBRequest) {
            DeleteBBRequest temp = (DeleteBBRequest) clientRequest;
            BillboardSQL bb = new BillboardSQL();
            bb.DeleteBillboard(temp.getBillboardName());
            oos.flush();
        }
        else if (clientRequest instanceof BBInfoRequest) {
            BBInfoRequest temp = (BBInfoRequest) clientRequest;
            BillboardSQL bb = new BillboardSQL();
            String info = bb.GetBillboardInfo(temp.getBillboardName());
            BBInfoReply bbInfoReply = new BBInfoReply(info);
            oos.writeObject(bbInfoReply);
            oos.flush();
        }
        else if (clientRequest instanceof ListBBRequest) {
            ListBBRequest listBBRequest = (ListBBRequest) clientRequest;
            BillboardSQL bb = new BillboardSQL();
            ListBBReply listBBReply = new ListBBReply(bb.ListBillboards(listBBRequest.getSessionToken()));
            oos.writeObject(listBBReply);
        }
        else if (clientRequest instanceof ScheduleBillboardRequest) {
            ScheduleBillboardRequest temp = (ScheduleBillboardRequest) clientRequest;
            ScheduleSQL Schedule = new ScheduleSQL();
            Schedule.ScheduleBillboard(temp.getBillboardName(),temp.getScheduledTime(), temp.getDuration(),temp.getReoccurType(),temp.getReoccurAmount());
            oos.flush();
        }
    }
}
