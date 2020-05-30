package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a request class that handle the edit user request, to execute the request,
 * it requires login user's session token, and some information of the user who is been editing,
 * this includes username and all permission. Password can be edited as well,
 * if there is no password passed in, the password of the user will not change.
 */
public class EditUserRequest implements Serializable {
    // Initialize all require variable.
    private SessionToken sessionToken;
    private String userName;
    private String userPassword;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;
    private boolean havePassword;

    /**
     * This is constructor with new password.
     * @param sessionToken login user's session token
     * @param userName edited user's username
     * @param userPassword edited user's new password
     * @param createBillboardsPermission edited user's create billboard permission
     * @param editAllBillboardPermission edited user's edit all billboard permission
     * @param scheduleBillboardsPermission edited user's schedule billboard permission
     * @param editUsersPermission edited user's edit user permission
     * @param havePassword a boolean tell the server if there is password
     */
    //build constructor and Getter functions
    public EditUserRequest(SessionToken sessionToken, String userName, String userPassword, boolean createBillboardsPermission,
                           boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission, boolean havePassword) {
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createBillboardsPermission = createBillboardsPermission;
        this.editAllBillboardPermission = editAllBillboardPermission;
        this.scheduleBillboardsPermission = scheduleBillboardsPermission;
        this.editUsersPermission = editUsersPermission;
        this.havePassword = havePassword;
    }

    /**
     * This is constructor without new password.
     * @param sessionToken login user's session token
     * @param userName edited user's username
     * @param createBillboardsPermission edited user's new password
     * @param editAllBillboardPermission edited user's create billboard permission
     * @param scheduleBillboardsPermission edited user's schedule billboard permission
     * @param editUsersPermission edited user's edit user permission
     * @param havePassword a boolean tell the server if there is password
     */
    public EditUserRequest(SessionToken sessionToken, String userName, boolean createBillboardsPermission,
                           boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission, boolean havePassword) {
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.createBillboardsPermission = createBillboardsPermission;
        this.editAllBillboardPermission = editAllBillboardPermission;
        this.scheduleBillboardsPermission = scheduleBillboardsPermission;
        this.editUsersPermission = editUsersPermission;
        this.havePassword = havePassword;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean isCreateBillboardsPermission() {
        return createBillboardsPermission;
    }

    public boolean isEditAllBillboardPermission() {
        return editAllBillboardPermission;
    }

    public boolean isScheduleBillboardsPermission() {
        return scheduleBillboardsPermission;
    }

    public boolean isEditUsersPermission() {
        return editUsersPermission;
    }

    public boolean isHavePassword() {
        return havePassword;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }
}
