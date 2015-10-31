/**
 * Created by Matthew on 10/30/2015.
 */
public class Names {
    //priv var
    int _id;
    String name;
    int groupid;

    //empty constructor
    public Names(){

    }

    //constructor
    public Names(int id, String name, int groupid){
        this._id = id;
        this.name = name;
        this.groupid = groupid;
    }

    //constructor
    public Names(String name, int groupid){
        this.name = name;
        this.groupid = groupid;
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
}
