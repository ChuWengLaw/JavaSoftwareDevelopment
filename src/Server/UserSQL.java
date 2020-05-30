package Server;

import ControlPanel.User;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Vector;

public class UserSQL {
    /**
     * @author Nicholas Tseng
     * This is a method that help passing the data of a user from database
     * and setup a user class with corresponding information.
     * @param user a user class
     * @param userName username of the target user.
     * @throws SQLException
     */
    static void setUserSQL(User user, String userName) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("SELECT * FROM  user where userName = ?");
        pstatement.setString(1, userName);
        ResultSet resultSet = pstatement.executeQuery();

        while (resultSet.next()) {
            if (userName.equals(resultSet.getString("userName"))) {
                user.setUserName(resultSet.getString("userName"));
                user.setCreateBillboardsPermission(resultSet.getBoolean("createBillboardsPermission"));
                user.setEditAllBillboardsPermission(resultSet.getBoolean("editAllBillboardPermission"));
                user.setScheduleBillboardsPermission(resultSet.getBoolean("scheduleBillboardsPermission"));
                user.setEditUsersPermission(resultSet.getBoolean("editUsersPermission"));
                break;
            }
        }

        pstatement.close();
    }

    /**
     * @author Nicholas Tseng
     * This is a method that help check if username and password matches each other
     * inside the database
     * in the database.
     * @param userName username of the target user.
     * @param userPassword password of the target user.
     * @return
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    static boolean checkPasswordSQL(String userName, String userPassword) throws SQLException, NoSuchAlgorithmException {
        boolean correctPassword = false;

        Statement statement = Server.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userName, userPassword, saltValue FROM user");

        while (resultSet.next()) {
            String hasedRecievedString = Server.hashAString(userPassword + resultSet.getString(3));

            if (userName.equals(resultSet.getString(1)) && hasedRecievedString.equals(resultSet.getString(2))) {
                correctPassword = true;
                break;
            }
        }

        statement.close();
        return correctPassword;
    }

    /**
     * @author Nicholas Tseng
     * This is a method that help create a new user in the database.
     * @param userName username of the new user
     * @param userPassword password of the new user
     * @param createBillboardsPermission create billboard permission of the new user
     * @param editAllBillboardPermission edit all billboard permission of the new user
     * @param scheduleBillboardsPermission schedule billboard permission of the new user
     * @param editUsersPermission edit users permission of the new user
     * @param saltValue salt value of the new user, this is for encrypting the password
     * @throws SQLException
     */
    static void createUserSQL(String userName, String userPassword,
                              boolean createBillboardsPermission, boolean editAllBillboardPermission,
                              boolean scheduleBillboardsPermission, boolean editUsersPermission, String saltValue) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("INSERT INTO user  " +
                "(userName, userPassword,  createBillboardsPermission, editAllBillboardPermission, scheduleBillboardsPermission, editUsersPermission, saltValue) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstatement.setString(1, userName);
        pstatement.setString(2, userPassword);
        pstatement.setBoolean(3, createBillboardsPermission);
        pstatement.setBoolean(4, editAllBillboardPermission);
        pstatement.setBoolean(5, scheduleBillboardsPermission);
        pstatement.setBoolean(6, editUsersPermission);
        pstatement.setString(7, saltValue);
        pstatement.executeUpdate();
        pstatement.close();
    }

    static void deleteUserBillboardSQL(String userName) throws SQLException {
        PreparedStatement deleteUserStatement = Server.connection.prepareStatement("DELETE FROM user WHERE userName = ?");
        PreparedStatement deleteUserBillboardStatement = Server.connection.prepareStatement("DELETE from Billboard WHERE userName =?");
        PreparedStatement deleteUserScheduleStatement = Server.connection.prepareStatement("DELETE from Schedule WHERE UserName =?");
        deleteUserStatement.setString(1, userName);
        deleteUserBillboardStatement.setString(1, userName);
        deleteUserScheduleStatement.setString(1, userName);
        deleteUserStatement.executeQuery();
        deleteUserBillboardStatement.executeQuery();
        deleteUserScheduleStatement.executeQuery();
        deleteUserBillboardStatement.close();
        deleteUserStatement.close();
        deleteUserScheduleStatement.close();
    }

    static JTable listUserSQL() throws SQLException {
        JTable table = new JTable();
        Statement listUserStatement = Server.connection.createStatement();
        ResultSet rs = listUserStatement.executeQuery(
                "select userName, CreateBillboardsPermission, EditAllBillboardPermission,"
                        + "ScheduleBillboardsPermission, EditUsersPermission from user");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Vector columnHeader = new Vector(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            columnHeader.add(rsmd.getColumnName(i));
        }
        Vector data = new Vector();
        Vector row = new Vector();

        while (rs.next()) {
            row = new Vector(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        table = new JTable(data, columnHeader);
        return table;
    }

    /**
     * @author Nicholas Tseng
     * This is a method that check if the user exisits in the database.
     * @param userName username of the target user.
     * @return
     * @throws SQLException
     */
    static boolean checkUserSQL(String userName) throws SQLException {
        boolean existing = false;

        Statement statement = Server.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userName FROM  user");

        while (resultSet.next()) {
            if (userName.equals(resultSet.getString(1))) {
                existing = true;
                break;
            }
        }

        statement.close();
        return existing;
    }

    /**
     * @author Nicholas Tseng
     * This is a method that edit a user in the database with a new password.
     * @param userName username of the target user
     * @param userPassword new password of the target user
     * @param createBillboardsPermission create billboard permission of the target user
     * @param editAllBillboardPermission edit all billboard permission of the target user
     * @param scheduleBillboardsPermission schedule billboard permission of the target user
     * @param editUsersPermission edit users permission of the target user
     * @param saltValue salt value of the new user, this is for encrypting the password
     * @throws SQLException
     */
    static void editUserSQL(String userName, String userPassword,
                            boolean createBillboardsPermission, boolean editAllBillboardPermission,
                            boolean scheduleBillboardsPermission, boolean editUsersPermission, String saltValue) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("UPDATE user " +
                "SET userPassword = ?,  createBillboardsPermission = ?, editAllBillboardPermission = ?, " +
                "scheduleBillboardsPermission = ?, editUsersPermission = ?, saltValue = ? WHERE userName = ? ");
        pstatement.setString(1, userPassword);
        pstatement.setBoolean(2, createBillboardsPermission);
        pstatement.setBoolean(3, editAllBillboardPermission);
        pstatement.setBoolean(4, scheduleBillboardsPermission);
        pstatement.setBoolean(5, editUsersPermission);
        pstatement.setString(6, saltValue);
        pstatement.setString(7, userName);
        pstatement.executeUpdate();
        pstatement.close();
    }

    /**
     * @author Nicholas Tseng
     * This is a method that edit a user in the database without a new password.
     * @param userName username of the target user
     * @param createBillboardsPermission create billboard permission of the target user
     * @param editAllBillboardPermission edit all billboard permission of the target user
     * @param scheduleBillboardsPermission schedule billboard permission of the target user
     * @param editUserPermission edit users permission of the target user
     * @throws SQLException
     */
    static void editUserSQL(String userName, boolean createBillboardsPermission, boolean editAllBillboardPermission,
                            boolean scheduleBillboardsPermission, boolean editUserPermission) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("UPDATE user " +
                "SET  createBillboardsPermission = ?, editAllBillboardPermission = ?, " +
                "scheduleBillboardsPermission = ?, editUsersPermission = ? WHERE userName = ? ");
        pstatement.setBoolean(1, createBillboardsPermission);
        pstatement.setBoolean(2, editAllBillboardPermission);
        pstatement.setBoolean(3, scheduleBillboardsPermission);
        pstatement.setBoolean(4, editUserPermission);
        pstatement.setString(5, userName);
        pstatement.executeUpdate();
        pstatement.close();
    }

    /**
     * @author Nicholas Tseng
     * This is a method that change password of a user in the database.
     * @param userName username of the target user
     * @param newPassword new password of the target user
     * @param saltString salt value of the new user, this is for encrypting the password
     * @throws SQLException
     */
    static void changePasswordSQL(String userName, String newPassword, String saltString) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("UPDATE user " +
                "SET userPassword = ?, saltValue = ? WHERE userName = ? ");

        pstatement.setString(1, newPassword);
        pstatement.setString(2, saltString);
        pstatement.setString(3, userName);
        pstatement.executeUpdate();
        pstatement.close();
    }
}
