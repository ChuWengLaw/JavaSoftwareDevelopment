package ControlPanel;

import Server.SessionToken;

import java.io.Serializable;


/**
 * @author Nicholas Tseng
 * This is a user class that holds the information of a user in the program.
 * A user with no information is possible to be created
 * and the information can be set up with the setters later.
 */
public class User implements Serializable {
    // Initialize all variables for necessary user information.
    private String userName;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;
    private SessionToken sessionToken;

    /**
     * This is an empty constructor for creating user with unknown information.
     */
    public User() {
    }

    /**
     * This is constructor for creating user with known information.
     * @param userName username
     * @param createBillboardsPermission create billboard permission of the user
     * @param editAllBillboardPermission edit all billboard permission of the user
     * @param scheduleBillboardsPermission schedule billboard permission of the user
     * @param editUsersPermission edit users permission of the user
     */
    public User(String userName, boolean createBillboardsPermission, boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission) {
        this.userName = userName;
        this.createBillboardsPermission = createBillboardsPermission;
        this.editAllBillboardPermission = editAllBillboardPermission;
        this.scheduleBillboardsPermission = scheduleBillboardsPermission;
        this.editUsersPermission = editUsersPermission;
    }

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


