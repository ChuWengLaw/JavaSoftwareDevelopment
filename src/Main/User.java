package Main;  //not sure if this is correct
import java.util.Scanner;    //remove once gui is set up

//Double check method prefixes  (public/private/static ect)
public class User {
    private String userID;
    private String userFName;
    private String userLName;
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
    public void setUserID(String userID){
        this.userID = userID;
        System.out.println(this.userID);
    }

    public void setUserFName(String userFName){
        this.userFName = userFName;
        System.out.println(this.userFName);
    }

    public void setUserLName(String userLName){
        this.userLName = userLName;
        System.out.println(this.userLName);
    }


    public void setPassword(char[] userPassword) {
        this.userPassword = String.valueOf(userPassword);
        System.out.println(this.userPassword);
    }

    public void setCreateBillboardsPermission(Boolean permission){
        this.createBillboardsPermission = permission;
    }

    public void setEditAllBillboardsPermission(Boolean permission){
        this.editAllBillboardPermission = permission;
    }

    public void setScheduleBillboardsPermission(Boolean permission){
        this.scheduleBillboardsPermission = permission;
    }

    public void setEditUsersPermission(Boolean permission){
        this.editUsersPermission = permission;
    }

    // Get Methods to be called to check user's information
    public String getUserID(){
        return userID;
    }

    public String getUserFName(){
        return userFName;
    }

    public String getUserLName(){
        return userLName;
    }

    public String getUserPassword(){
        return userPassword;
    }

    // Get Methods to be called to check if user has permissions to call above functions
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


