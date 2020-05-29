package Server;

import ControlPanel.User;
import ControlPanel.schedule.CalanderScheduleGUI;
import Server.Reply.*;
import Server.Request.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static Server.UserSQL.*;

public class Server {
    public static Connection connection;
    public static Statement statement;
    private static String BillBoardName = "";
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
                    + "InfoColour VARCHAR(30)" + ");";

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

    /**
     * This method starts up the server socket connection and receives requests from client
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        /* Initiate database connection */
        initDBConnection();

        /* Setup socket connection */
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            // read the network properties from network.props file
            in = new FileInputStream("./network.props");
            props.load(in);
            in.close();

            // specify the socket port
            int port = Integer.parseInt(props.getProperty("port"));
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println(e);
        }

        for (; ; ) {
            // Continues running to receive connections from client
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

    /**
     * This method instantiates a connection to MariaDB.
     * Billboard, User and Schedule table will be created upon server start up.
     *
     * @author Law
     */
    private static void initDBConnection() {
        // Connects to MariaDB
        connection = DBConnection.newConnection();
        try {
            // Creates tables
            statement = connection.createStatement();
            statement.execute(CREATE_BILLBOARD_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_SCHEDULE_TABLE);

            // Username and Password are added.
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                String testAdmin = "admin";
                String testPassword = "test1";
                String testSaltString = randomString();
                boolean AdminExists = false;
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method hashes the salted password of user account.
     *
     * @param hashString salted password to be hashed
     * @return salted hashed password
     * @throws NoSuchAlgorithmException
     * @author Nic
     */
    public static String hashAString(String hashString) throws NoSuchAlgorithmException {
        // Hashes the password using SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(hashString.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    /**
     * This method salts password of user account.
     *
     * @return salted password
     * @author Nicholas Tseng
     */
    public static String randomString() {
        Random rng = new Random();
        byte[] saltBytes = new byte[32];
        rng.nextBytes(saltBytes);
        StringBuffer sb = new StringBuffer();

        for (byte b : saltBytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    /**
     * This method checks the availability of the session token, token expires after 24 hours
     *
     * @param sessionToken Session token to be assigned to user upon logging in
     * @return state of token availability
     * @author Nicholas Tseng
     */
    private static boolean tokenCheck(SessionToken sessionToken) {
        boolean tokenAvailableState;

        // This is used for testing, so the token will expire after one minute of hanging.
        if (sessionToken.getUsedTime().plusMinutes(5).isAfter(LocalDateTime.now())) {
            tokenAvailableState = true;
        } else {
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
     * This method filters and executes the request received from client in server.
     * Server checks the validity of the session tokens and return an appropriate reply to client
     *
     * @param clientRequest Request sent by client via stateless socket connection after button
     *                      presses.
     * @param oos           Output stream
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void requestExecute(Object clientRequest, ObjectOutputStream oos) throws SQLException, NoSuchAlgorithmException, IOException {
        // If the request is an instance of login request
        if (clientRequest instanceof LoginRequest) {
            LoginRequest loginRequest = (LoginRequest) clientRequest;
            boolean loginState = checkPasswordSQL(loginRequest.getUserName(), loginRequest.getPassword());

            if (loginState) {
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
        // If the request is an instance of create user request
        else if (clientRequest instanceof CreateUserRequest) {
            CreateUserRequest createUserRequest = (CreateUserRequest) clientRequest;

            SessionToken sessionToken = findSessionToken(createUserRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(createUserRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                boolean createState = !checkUserSQL(createUserRequest.getUserName());

                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);

                if (createState) {
                    String saltString = randomString();
                    String hashedPassword = hashAString(createUserRequest.getUserPassword() + saltString);
                    createUserSQL(createUserRequest.getUserName(), hashedPassword, createUserRequest.isCreateBillboardsPermission(),
                            createUserRequest.isEditAllBillboardPermission(), createUserRequest.isScheduleBillboardsPermission(),
                            createUserRequest.isEditUsersPermission(), saltString);

                    GeneralReply generalReply = new GeneralReply(sessionToken, true);
                    oos.writeObject(generalReply);

                }
                else{
                    GeneralReply generalReply = new GeneralReply(sessionToken, false);

                    oos.writeObject(generalReply);
                }
            }

            oos.flush();
        }
        // If the request is an instance of search user request
        else if (clientRequest instanceof SearchRequest) {
            SearchRequest searchRequest = (SearchRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(searchRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(searchRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                boolean searchState = checkUserSQL(searchRequest.getUserName());

                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());

                // Reply based on the existence of the user name that was searched.
                if (searchState) {
                    User user = new User();
                    setUserSQL(user, searchRequest.getUserName());
                    SearchReply searchReply = new SearchReply(sessionToken, true, user);
                    oos.writeObject(searchReply);
                } else {
                    SearchReply searchReply = new SearchReply(sessionToken, false);
                    oos.writeObject(searchReply);
                }
            }

            oos.flush();
        }
        // If the request is an instance of edit user request
        else if (clientRequest instanceof EditUserRequest) {
            EditUserRequest editUserRequest = (EditUserRequest) clientRequest;
            boolean havePassword = editUserRequest.isHavePassword();
            SessionToken sessionToken = findSessionToken(editUserRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(editUserRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());

                // Edit SQL depends whether a new password is passed in, so when the password field is empty,
                // the password won't change.
                if (havePassword) {
                    String saltString = randomString();
                    String hasedPassword = hashAString(editUserRequest.getUserPassword() + saltString);
                    editUserSQL(editUserRequest.getUserName(), hasedPassword, editUserRequest.isCreateBillboardsPermission(), editUserRequest.isEditAllBillboardPermission(),
                            editUserRequest.isScheduleBillboardsPermission(), editUserRequest.isEditUsersPermission(), saltString);
                    GeneralReply generalReply = new GeneralReply(sessionToken,true);
                    oos.writeObject(generalReply);
                } else {
                    editUserSQL(editUserRequest.getUserName(), editUserRequest.isCreateBillboardsPermission(), editUserRequest.isEditAllBillboardPermission(),
                            editUserRequest.isScheduleBillboardsPermission(), editUserRequest.isEditUsersPermission());
                    GeneralReply generalReply = new GeneralReply(sessionToken,true);
                    oos.writeObject(generalReply);
                }
            }

            oos.flush();
        }
        // If the request is an instance of change password request
        else if (clientRequest instanceof ChangePasswordRequest) {
            ChangePasswordRequest changePasswordRequest = (ChangePasswordRequest) clientRequest;
            String saltString = randomString();
            String hashedPassword = hashAString(changePasswordRequest.getNewPassword() + saltString);
            SessionToken sessionToken = findSessionToken(changePasswordRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(changePasswordRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                changePasswordSQL(changePasswordRequest.getUserName(), hashedPassword, saltString);
                GeneralReply generalReply = new GeneralReply(sessionToken,true);
                oos.writeObject(generalReply);
            }
            oos.flush();
        }
        // If the request is an instance of logout request
        else if (clientRequest instanceof LogoutRequest) {
            LogoutRequest logoutRequest = (LogoutRequest) clientRequest;

            // Remove session token from the list
            for (int i = 0; i <= sessionTokens.size(); i++) {
                if (sessionTokens.get(i).getSessionTokenString().equals(logoutRequest.getSessionToken().getSessionTokenString())) {
                    sessionTokens.remove(i);
                    break;
                }
            }

            LogoutReply logoutReply = new LogoutReply(false);
            oos.writeObject(logoutReply);
            oos.flush();
        }
        // If the request is an instance of delete user request
        else if (clientRequest instanceof DeleteUserRequest) {
            DeleteUserRequest deleteUser = (DeleteUserRequest) clientRequest;
            boolean checkDeleteUser = checkUserSQL(deleteUser.getUserName());

            SessionToken sessionToken = findSessionToken(deleteUser.getSessionToken());

            if(!tokenCheck(deleteUser.getSessionToken())){
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else{
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());

                if (checkDeleteUser){
                    deleteUserBillboardSQL(deleteUser.getUserName());
                    GeneralReply generalReply = new GeneralReply(sessionToken,true);
                    oos.writeObject(generalReply);
                }
                else{
                    GeneralReply generalReply = new GeneralReply(sessionToken,false);
                    oos.writeObject(generalReply);
                }

                oos.flush();
            }

        }
        // If the request is an instance of list all users request
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
                for (int i = 0; i <= sessionTokens.size(); i++) {
                    if (sessionTokens.get(i).getSessionTokenString().equals(sessionToken.getSessionTokenString())) {
                        sessionTokens.get(i).setUsedTime(LocalDateTime.now());
                        break;
                    }
                }
                ListUserReply listUserReply = new ListUserReply(sessionToken, listUserSQL(), true);
                oos.writeObject(listUserReply);
                oos.flush();
            }
        }
        // If the request is an instance of create/edit billboard request
        else if (clientRequest instanceof CreateBBRequest) {
            CreateBBRequest temp = (CreateBBRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(temp.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(temp.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;

                // if the user has the permission to create a billboard
                if (temp.getCreateBillboardPermission()) {
                    // Execute create/edit query and return general reply indicating success
                    try {
                        BillboardSQL bb = new BillboardSQL();
                        bb.CreateBillboard(temp.getBillboardName(), temp.getUserName(), temp.getTextColour(), temp.getBackgroundColour(),
                                temp.getMessage(), temp.getImage(), temp.getInformation(), temp.getInformationColour());
                        generalReply = new GeneralReply(sessionToken,true);
                    } catch (Exception e) {
                        generalReply = new GeneralReply(sessionToken,false);
                    }
                }
                else {
                    generalReply = new GeneralReply(sessionToken,false);
                }
                oos.writeObject(generalReply);
            }
            oos.flush();
        }
        // If the request is an instance of edit billboard request
        else if (clientRequest instanceof EditBBRequest) {
            EditBBRequest temp = (EditBBRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(temp.getSessionToken());
            BillboardSQL bb = new BillboardSQL();

            // Find the session token in the list.
            for(int i  = 0; i <= sessionTokens.size(); i++){
                if(sessionTokens.get(i).getSessionTokenString().equals(temp.getSessionToken().getSessionTokenString())){
                    sessionToken = sessionTokens.get(i);
                    break;
                }
            }
            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(temp.getSessionToken())){
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            }
            else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;
                // Execute edit query and return general reply indicating success
                try {
                    // update edited billboard
                    if (temp.getBillboardName() == null) {
                        bb.EditBillboard(BillBoardName, temp.getEditTextColour(), temp.getEditBGColour(),
                                temp.getEditMsg(), temp.getEditImg(), temp.getEditInfo(), temp.getEditInfoColour());
                        new MakeXMLFile(BillBoardName);
                        generalReply = new GeneralReply(temp.getSessionToken(),true);
                        oos.writeObject(generalReply);
                    }
                    // return the contents of the billboard
                    else {
                        BillBoardName = temp.getBillboardName();
                        boolean checkOriginOwner = bb.checkBillboardUser(BillBoardName, temp.getLoginUser());
                        // if the user is the owner of the billboard
                        if (checkOriginOwner) {
                            // if the user has edit all billboards or create billboard permission
                            if (temp.getEditAllBillboardsPermission() || (temp.getCreateBillboardPermission() && !CalanderScheduleGUI.IsCurrentlyScheduled(BillBoardName))) {
                                bb.EditBillboard(BillBoardName);
                                EditBBReply editBBReply = new EditBBReply(temp.getSessionToken(), bb.textColour, bb.backgroundColour, bb.message,
                                        bb.image, bb.information, bb.informationColour);
                                oos.writeObject(editBBReply);
                            }
                            else {
                                generalReply = new GeneralReply(temp.getSessionToken(),false);
                                oos.writeObject(generalReply);
                            }
                        }
                        // if the user is editing billboard owned by other user
                        else {
                            // if the user has edit all billboards permission
                            if (temp.getEditAllBillboardsPermission()) {
                                bb.EditBillboard(BillBoardName);
                                EditBBReply editBBReply = new EditBBReply(temp.getSessionToken(), bb.textColour, bb.backgroundColour, bb.message,
                                        bb.image, bb.information, bb.informationColour);
                                oos.writeObject(editBBReply);
                            }
                            else {
                                generalReply = new GeneralReply(temp.getSessionToken(),false);
                                oos.writeObject(generalReply);
                            }
                        }
                    }
                } catch (Exception e) {
                    generalReply = new GeneralReply(temp.getSessionToken(),false);
                    oos.writeObject(generalReply);
                }
            }
            oos.flush();
        }
        // If the request is an instance of delete billboard request
        else if (clientRequest instanceof DeleteBBRequest) {
            DeleteBBRequest temp = (DeleteBBRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(temp.getSessionToken());
            BillboardSQL bb = new BillboardSQL();

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(temp.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;

                // Execute delete query and return general reply indicating success
                try {
                    BillBoardName = temp.getBillboardName();
                    boolean checkOriginOwner = bb.checkBillboardUser(BillBoardName, temp.getLoginUser());
                    // if the user is the owner of the billboard
                    if (checkOriginOwner) {
                        // if the user has edit all billboards or create billboard permission
                        if (temp.getEditAllBillboardsPermission() || (temp.getCreateBillboardPermission() && !CalanderScheduleGUI.IsCurrentlyScheduled(BillBoardName))) {
                            bb.DeleteBillboard(temp.getBillboardName());
                            File deleteFile = new File("src/xmlBillboards/" + temp.getBillboardName() + ".xml");
                            deleteFile.delete();
                            generalReply = new GeneralReply(sessionToken,true);
                        }
                        else {
                            generalReply = new GeneralReply(temp.getSessionToken(),false);
                        }
                    }
                    // if the user is deleting billboard owned by other user
                    else {
                        // if the user has edit all billboards permission
                        if (temp.getEditAllBillboardsPermission()) {
                            bb.DeleteBillboard(temp.getBillboardName());
                            File deleteFile = new File("src/xmlBillboards/" + temp.getBillboardName() + ".xml");
                            deleteFile.delete();
                            generalReply = new GeneralReply(temp.getSessionToken(),true);
                        }
                        else {
                            generalReply = new GeneralReply(temp.getSessionToken(),false);
                        }
                    }
                    oos.writeObject(generalReply);
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken,false);
                    oos.writeObject(generalReply);
                }
            }
            oos.flush();
        }
        // If the request is an instance of get billboard's information request
        else if (clientRequest instanceof BBInfoRequest) {
            BBInfoRequest temp = (BBInfoRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(temp.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(temp.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;

                // Return a reply object containing the information of the billboard
                try {
                    BillboardSQL bb = new BillboardSQL();
                    bb.GetBillboardInfo(temp.getBillboardName());
                    String info = bb.GetBillboardInfo(temp.getBillboardName());
                    BBInfoReply bbInfoReply = new BBInfoReply(sessionToken, info);
                    oos.writeObject(bbInfoReply);
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken,false);
                    oos.writeObject(generalReply);
                }
            }
            oos.flush();
        }
        // If the request is an instance of list all billboards request
        else if (clientRequest instanceof ListBBRequest) {
            ListBBRequest listBBRequest = (ListBBRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(listBBRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(listBBRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;

                // Return a reply object to the client containing JTable of billboards
                try {
                    BillboardSQL bb = new BillboardSQL();
                    ListBBReply listBBReply = new ListBBReply(sessionToken, bb.ListBillboards());
                    oos.writeObject(listBBReply);
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken,false);
                    oos.writeObject(generalReply);
                }
            }
            oos.flush();
        } else if (clientRequest instanceof ScheduleBillboardRequest) {
            ScheduleBillboardRequest scheduleBillboardRequest = (ScheduleBillboardRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(scheduleBillboardRequest.getSessionToken());
            if (!tokenCheck(scheduleBillboardRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;
                try {
                    ScheduleSQL Schedule = new ScheduleSQL();
                    Schedule.ScheduleBillboard(scheduleBillboardRequest.getBillboardName(), scheduleBillboardRequest.getScheduledTime(), scheduleBillboardRequest.getDuration(), scheduleBillboardRequest.getReoccurType(), scheduleBillboardRequest.getReoccurAmount());
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken, false);
                    oos.writeObject(generalReply);
                }
            }
            oos.flush();
        } else if (clientRequest instanceof DeleteScheduleRequest) {
            DeleteScheduleRequest deleteScheduleRequest = (DeleteScheduleRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(deleteScheduleRequest.getSessionToken());
            if (!tokenCheck(deleteScheduleRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;
                try {
                    ScheduleSQL Schedule = new ScheduleSQL();
                    Schedule.DeleteSchedule(deleteScheduleRequest.getScheduledName(), deleteScheduleRequest.getScheduledTime());
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken, false);
                    oos.writeObject(generalReply);
                }
            }
            oos.flush();
        } else if (clientRequest instanceof WeeklyScheduleRequest) {
            WeeklyScheduleRequest weeklyscheduleRequest = (WeeklyScheduleRequest) clientRequest;
            ScheduleSQL Schedule = new ScheduleSQL();
            WeeklyScheduleReply weeklyscheduleReply = new WeeklyScheduleReply(Schedule.ScheduledInformation());
            oos.writeObject(weeklyscheduleReply);
        }
        else if (clientRequest instanceof GetCurrentScheduledRequest) {
            GetCurrentScheduledRequest GetCurrentScheduledRequest = (GetCurrentScheduledRequest) clientRequest;
            ScheduleSQL Schedule = new ScheduleSQL();
            GetCurrentScheduledReply getcurrentscheduledReply = new GetCurrentScheduledReply(Schedule.GetTitleCurrentScheduled(), true);
            oos.writeObject(getcurrentscheduledReply);
        }
        else if (clientRequest instanceof ListScheduleRequest) {
            ListScheduleRequest listScheduleRequest = (ListScheduleRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(listScheduleRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(listScheduleRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;

                // Return a reply object to the client containing JTable of billboards
                try {
                    ScheduleSQL Schedule = new ScheduleSQL();
                    ListScheduleReply listScheduleReply = new ListScheduleReply(sessionToken, Schedule.GetScheduledInfo(sessionToken));
                    oos.writeObject(listScheduleReply);
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken, false);
                    oos.writeObject(generalReply);
                }

            }
            oos.flush();
        }
            // If the request in an instance of import/export billboard request
        else if (clientRequest instanceof XmlRequest) {
            XmlRequest xmlRequest = (XmlRequest) clientRequest;
            SessionToken sessionToken = findSessionToken(xmlRequest.getSessionToken());

            // Remove session token from the list and send a logout request if it expired.
            if (!tokenCheck(xmlRequest.getSessionToken())) {
                sessionTokens.remove(sessionToken);
                LogoutReply logoutReply = new LogoutReply(true);
                oos.writeObject(logoutReply);
            } else {
                // Reset the used time of the session token.
                resetSessionTokenTime(sessionToken);
                sessionToken.setUsedTime(LocalDateTime.now());
                GeneralReply generalReply;

                // Return a reply object to the client containing JTable of billboards
                try {
                    // if the user exports a billboard
                    if (xmlRequest.getXmlFile() == null && xmlRequest.isExportState()) {
                        String xmlName = xmlRequest.getXmlName() + ".xml";
                        File exportFile = new File("src/xmlBillboards/" + xmlName);
                        XmlReply xmlReply = new XmlReply(sessionToken, exportFile);
                        oos.writeObject(xmlReply);
                    }
                    // if the user creates a billboard
                    if (xmlRequest.getXmlFile() == null && !xmlRequest.isExportState()) {
                        new MakeXMLFile(xmlRequest.getXmlName());
                        generalReply = new GeneralReply(sessionToken, true);
                        oos.writeObject(generalReply);
                    }
                    // if the user imports a billboard
                    else {
                        // copy uploaded new xml file to path then extract its contents and save it in db
                        String newFileName = xmlRequest.getXmlFile().getName();
                        String billboardName = newFileName.replaceFirst("[.][^.]+$", "");
                        var newPath = new File("src/xmlBillboards/" + newFileName);
                        Files.copy(xmlRequest.getXmlFile().toPath(), newPath.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        ExtractFromXML extractFromXML = new ExtractFromXML(newFileName);
                        BillboardSQL bb = new BillboardSQL();
                        bb.CreateBillboard(billboardName, xmlRequest.getUserName(), extractFromXML.TxtColourStr, extractFromXML.BGColourStr,
                                extractFromXML.message, extractFromXML.image, extractFromXML.information, extractFromXML.InfoColourStr);
                        generalReply = new GeneralReply(sessionToken, true);
                        oos.writeObject(generalReply);
                    }
                } catch (Exception e) {
                    generalReply = new GeneralReply(sessionToken,false);
                    oos.writeObject(generalReply);
                }
                oos.flush();
            }
        }
    }
}

