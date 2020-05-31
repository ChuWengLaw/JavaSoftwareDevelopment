package ControlPanel;

import ControlPanel.user.*;

import javax.swing.*;

/**
 * This is the main class for control panel.
 */
public class Main {
    public static User loginUser = new User();

    // Setting up windows
    public static MenuWin menuWin = new MenuWin();
    public static UserManagementWin userManagementWin = new UserManagementWin();
    public static UserProfileWin userProfileWin = new UserProfileWin();
    public static ChangePasswordWin changePasswordWin = new ChangePasswordWin();
    public static CreateUserWin createUserWin = new CreateUserWin();
    public static EditUserWin editUserWin = new EditUserWin();
    public static DeleteUserWin deleteUserWin = new DeleteUserWin();
    public static ListUserWin listUserWin = new ListUserWin();
    public static SearchUserInfoWin searchUserWin = new SearchUserInfoWin();

    /**
     * This method call a new login window once the program is run.
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LoginWin());
    }
}

