package cse110.com.cse110_chores;

/**
 * Created by Minh on 11/14/2015.
 */
public class ChoreName {
    int _id;
    String choreName;
    int i;
    int groupid;

    public ChoreName(){
    }

    public ChoreName(int _id, String choreName, int i, int groupid){
        this._id = _id;
        this.choreName = choreName;
        this.i = i;
        this.groupid = groupid;
    }

    public ChoreName(String choreName, int i, int groupid){
        this.choreName = choreName;
        this.i = i;
        this.groupid = groupid;
    }

    public int getId(){
        return this._id;
    }

    public String getChoreName(){
        return this.choreName;
    }

    public void seti(int i){
        this.i = i;
    }

    public int geti(){
        return this.i;
    }

    public int getGroupid(){
        return this.groupid;
    }
}
