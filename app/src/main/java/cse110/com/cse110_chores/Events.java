package cse110.com.cse110_chores;

/**
 * Created by Matthew on 10/29/2015.
 */
public class Events {
    //private vars
    int _id;
    String name;
    int eventStartDate;
    int eventEndDate;
    int eventStartTime;
    int eventEndTime;
    int groupid;
    boolean found;

    //empty constructor
    public Events(){
        this.found = false;
    }

    //constructor
    public Events(int id, String name, int eventStartDate, int eventEndDate, int eventStartTime,
                  int eventEndTime, int groupid){
        this._id = id;
        this.name = name;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.groupid = groupid;
        this.found = true;
    }

    //constructor without id
    public Events(String name, int eventStartDate, int eventEndDate, int eventStartTime,
                  int eventEndTime, int groupid) {
        this.name = name;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
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

    //get eventStartDate
    public int getEventStartDate(){
        return this.eventStartDate;
    }

    //get eventEndDate
    public int getEventEndDate(){
        return this.eventEndDate;
    }

    //get eventStartTime
    public int getEventStartTime(){
        return this.eventStartTime;
    }

    //get eventEndTime
    public int getEventEndTime(){
        return this.eventEndTime;
    }

    public int getGroupid(){
        return this.groupid;
    }

    public boolean getFound(){
        return this.found;
    }
}
