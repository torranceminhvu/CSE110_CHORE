/**
 * Created by Matthew on 10/29/2015.
 */
public class Groups {
    //private vars
    int _id;
    String username;
    String password;

    //empty constructor
    public Groups(){

    }

    //constructor
    public Groups(int id, String username, String password){
        this._id = id;
        this.username = username;
        this.password = password;
    }

    //constructor
    public Groups(String username, String password){
        this.username = username;
        this.password = password;
    }

    //get id
    public int getId(){
        return this._id;
    }

    //get username
    public String getUsername(){
        return this.username;
    }

    //get password
    public String getPassword(){
        return this.password;
    }
}
