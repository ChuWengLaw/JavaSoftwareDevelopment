package ControlPanel;

import Server.SessionToken;

import java.io.Serializable;


//Double check method prefixes  (public/private/static ect)
public class User implements Serializable {
    private String userName;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;
    private SessionToken sessionToken;

//Default constructor should throw an error because below info is required

    //Constructor
    public User() {
    }

    public User(String userName, boolean createBillboardsPermission, boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission) {
        this.userName = userName;
        this.createBillboardsPermission = createBillboardsPermission;
        this.editAllBillboardPermission = editAllBillboardPermission;
        this.scheduleBillboardsPermission = scheduleBillboardsPermission;
        this.editUsersPermission = editUsersPermission;
    }

    // Set method to store user information
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreateBillboardsPermission(Boolean permission) {
        this.createBillboardsPermission = permission;
    }

    public void setEditAllBillboardsPermission(Boolean permission) {
        this.editAllBillboardPermission = permission;
    }

    public void setScheduleBillboardsPermission(Boolean permission) {
        this.scheduleBillboardsPermission = permission;
    }

    public void setEditUsersPermission(Boolean permission) {
        this.editUsersPermission = permission;
    }

    // Get Methods to be called to check user's information
    public String getUserName() {
        return userName;
    }

    public Boolean getCreateBillboardsPermission() {
        return createBillboardsPermission;
    }

    public Boolean getEditAllBillboardPermission() {
        return editAllBillboardPermission;
    }

    public Boolean getScheduleBillboardsPermission() {
        return scheduleBillboardsPermission;
    }

    public Boolean getEditUsersPermission() {
        return editUsersPermission;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
    }
}


