package cse110.com.cse110_chores;

/**
 * Created by Matthew on 10/29/2015.
 */
public class Groups {
    //private vars
    int _id;
    String username;
    String password;
    boolean found;

    //empty constructor
    public Groups(){
        this.found = false;
    }

    //constructor
    public Groups(int id, String username, String password){
        this._id = id;
        this.username = username;
        this.password = password;
        this.found = true;
    }

    //constructor
    public Groups(String username, String password){
        this.username = username;
        this.password = password;
        this.found = true;
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

    public boolean getFound(){
        return this.found;
    }
}
