package cse110.com.cse110_chores;

/**
 * Created by Minh on 11/14/2015.
 */
public class ChoreName {
    int _id;
    String choreName;
    int counter = 0;
    int frequency;
    int startTime;
    int i;
    int groupid;

    public ChoreName(){
    }

    public ChoreName(int _id, String choreName, int i, int counter, int frequency, int startTime, int groupid){
        this._id = _id;
        this.choreName = choreName;
        this.counter = counter;
        this.i = i;
        this.frequency = frequency;
        this.startTime = startTime;
        this.groupid = groupid;
    }

    public ChoreName(String choreName, int i, int counter, int frequency, int startTime, int groupid){
        this.choreName = choreName;
        this.i = i;
        this.frequency = frequency;
        this.counter = counter;
        this.startTime = startTime;
        this.groupid = groupid;
    }

    public ChoreName(String choreName, int i, int frequency, int startTime, int groupid){
        this.choreName = choreName;
        this.i = i;
        this.frequency = frequency;
        this.startTime = startTime;
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

    public int getCounter(){
        return this.counter;
    }

    public void setCounter(int i){
        this.counter = i;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public void setStartTime(int i){
        this.startTime = i;
    }
}
