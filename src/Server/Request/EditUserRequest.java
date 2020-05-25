package Server.Request;

import Server.SessionToken;

import java.io.Serializable;

public class EditUserRequest implements Serializable {
    private SessionToken sessionToken;
    private String userName;
    private String userPassword;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;
    private boolean havePassword;

    public EditUserRequest(SessionToken sessionToken, String userName, String userPassword,  boolean createBillboardsPermission,
                           boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission, boolean havePassword){
        this.sessionToken = sessionToken;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createBillboardsPermission = createBillboardsPermission;
        this.editAllBillboardPermission = editAllBillboardPermission;
        this.scheduleBillboardsPermission = scheduleBillboardsPermission;
        this.editUsersPermission = editUsersPermission;
        this.havePassword = havePassword;
    }

    public EditUserRequest(String userName,  boolean createBillboardsPermission,
                           boolean editAllBillboardPermission, boolean scheduleBillboardsPermission, boolean editUsersPermission, boolean havePassword){
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
