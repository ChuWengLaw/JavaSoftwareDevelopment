package ControlPanel;

import ControlPanel.user.*;

import javax.swing.*;


public class Main {
    public static User loginUser = new User();

    // Setting up windows
    public static MenuWin menuWin = new MenuWin();
    public statiserManagementWin userManagementWin = new UserManagementWin();
    public static UserProfileWin userProfileWin = new UserProfileWin();
    public static ChangePasswordWin changePasswordWin = new ChangePasswordWin();
    public static CreateUserWin createUserWin = new CreateUserWin();
    public static EditUserWin editUserWin = new EditUserWin();
    public static DeleteUserWin deleteUserWin = new DeleteUserWin();
    public static ListUserWin listUserWin = new ListUserWin();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LoginWin());
    }
}

