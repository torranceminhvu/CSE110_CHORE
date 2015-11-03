package cse110.com.cse110_chores;

/**
 * Created by Matthew on 10/29/2015.
 */


public class Chores {
    //priv vars
    int _id;
    boolean found;
    String name;
    String frequency;
    int groupid;

    //empty Constructor
    public Chores(){
        this.found = false;
    }

    //constructor
    public Chores(int id, String name, String frequency, int groupid){
        this._id = id;
        this.name = name;
        this.frequency = frequency;
        this.groupid = groupid;
        this.found = true;
    }

    public Chores(String name, String frequency, int groupid){
        this.name = name;
        this.frequency = frequency;
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

    //get frequency
    public String getFrequency(){
        return this.frequency;
    }

    //set frequency
    public void setFrequency(String frequency){
        this.frequency = frequency;
    }

    public int getGroupid(){
        return this.groupid;
    }

    public boolean getFound(){
        return this.found;
    }
}
