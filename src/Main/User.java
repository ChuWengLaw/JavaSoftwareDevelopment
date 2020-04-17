import


public class User {

    String UserName;
    String UserPassword;
    boolean CreateBillboardsPermission;
    boolean EditAllBillboardPermission;
    boolean ScheduleBillboardsPermission;
    boolean EditUsersPermission;

//Defaul constructor should throw an error because below info is required
    //Constructor
    public User(String UserName, String UserPassword, boolean CreateBillboardsPermission, boolean EditAllBillboardPermission, boolean ScheduleBillboardsPermission, boolean EditUsersPermission) {
        this.UserName = UserNamePermission;
        this.UserPassword = UserPasswordPermission;
        this.CreateBillboardsPermission;
        this.EditAllBillboardPermission = EditAllBillboardPermission;
        this.EditUsersPermission = EditUsersPermission;
    }




}


    //Global Variable array of object users placeholder for server communications
    public User Users[] = {};

public void GetUsers(){
    //Code that connects to Server and retrieves all User Infomation
    //filler code
    Users[] = {}  //empty array of the users, replace with code that retrieves the actual user infomation
}

public void DeleteUser(String UserName)
{
    GetUsers();
    for (int i = 0; i<Users.length;i++){
        if (Users[i].UserName = Username)
        //send SQL code to the database
    }

}