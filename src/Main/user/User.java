package Main.user;  //not sure if this is correct
import Main.Main;

import java.util.Scanner;    //remove once gui is set up

//Double check method prefixes  (public/private/static ect)
public class User {
    private String userName;
    private String userPassword;
    private boolean createBillboardsPermission;
    private boolean editAllBillboardPermission;
    private boolean scheduleBillboardsPermission;
    private boolean editUsersPermission;

//Default constructor should throw an error because below info is required

    //Constructor
    public User() {

    }

    // Set method to store user information
    public void setUserName(String userName){
        this.userName = userName;
        System.out.println(this.userName);
    }

    public void setPassword(char[] userPassword) {
        this.userPassword = String.valueOf(userPassword);
        System.out.println(this.userPassword);
    }

    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
        System.out.println(this.userPassword);
    }

    public void setCreateBillboardsPermission(Boolean permission){
        this.createBillboardsPermission = permission;
        System.out.println(this.createBillboardsPermission);
    }

    public void setEditAllBillboardsPermission(Boolean permission){
        this.editAllBillboardPermission = permission;
        System.out.println(this.editAllBillboardPermission);
    }

    public void setScheduleBillboardsPermission(Boolean permission){
        this.scheduleBillboardsPermission = permission;
        System.out.println(this.scheduleBillboardsPermission);
    }

    public void setEditUsersPermission(Boolean permission){
        this.editUsersPermission = permission;
        System.out.println(this.editUsersPermission);
    }

    // Get Methods to be called to check user's information
    public String getUserName(){
        return userName;
    }

    public String getUserPassword(){
        return userPassword;
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

}


