package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

/**
 * @author Nicholas Tseng
 * This is a request class that handle the create user request, to execute the request,
 * it requires login user's session token, and all information of the new user
 * who is created. This includes username, password and all of the permissions.
 */
public class CreateUserRequest implements Serializable {
    // Initialize all require variables.
    private SessionToken sessionToken;
    private String userName;
    private String userPassword;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;

    /**
     * This is the constructor.
     *
     * @param sessionToken                 login user's session token
     * @param userName                     new user's username
     * @param userPassword                 new user's password
     * @param createBillboardsPermission   new user's create billboards permission
     * @param editAllBillboardPermission   new user's edit billboard permission
     * @param scheduleBillboardsPermission new user's schedule billboards permission
     * @param editUsersPermission          new user's edit users permission
     */

    //build constructor and Getter functions
    public CreateUserRequest(SessionToken sessionToken, String userName, String userPassword, boolean createBillboardsPermission,
                             boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission) {
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createBillboardsPermission = createBillboardsPermission;
        this.editAllBillboardPermission = editAllBillboardPermission;
        this.scheduleBillboardsPermission = scheduleBillboardsPermission;
        this.editUsersPermission = editUsersPermission;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
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
}
