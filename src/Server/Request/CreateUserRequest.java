package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class CreateUserRequest implements Serializable{
    private SessionToken sessionToken;
    private String userName;
    private String userPassword;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;

    public CreateUserRequest(SessionToken sessionToken, String userName, String userPassword,  boolean createBillboardsPermission,
                             boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission){
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
