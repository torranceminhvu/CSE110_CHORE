package cse110.com.cse110_chores;

/**
 * Created by Matthew on 10/30/2015.
 */
public class Names {
    //priv var
    int _id;
    String name;
    int groupid;
    boolean found;

    //empty constructor
    public Names(){
        this.found = false;
    }

    //constructor
    public Names(int id, String name, int groupid){
        this._id = id;
        this.name = name;
        this.groupid = groupid;
        this.found = true;
    }

    //constructor
    public Names(String name, int groupid){
        this.name = name;
        this.groupid = groupid;
        this.found = true;
    }

    //get id
    public int getId(){
        return this._id;
    }

    //get name
    public String getName(){
        return this.name;
    }

    //set name
    public void setName(String name){
        this.name = name;
    }

    public int getGroupid(){
        return this.groupid;
    }

    public boolean getFound(){
        return this.found;
    }
}
