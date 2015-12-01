package cse110.com.cse110_chores;

/**
 * Created by Matthew on 10/29/2015.
 */
public class Events {
    //private vars
    int _id;
    String name;
    String description;
    String eventDate;
    String eventStartTime;
    String eventEndTime;
    int groupid;
    boolean found;

    //empty constructor
    public Events(){
        this.found = false;
    }

    //constructor
    public Events(int id, String name, String eventDate, String eventStartTime,
                  String eventEndTime, String description, int groupid){
        this._id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.description = description;
        this.groupid = groupid;
        this.found = true;
    }

    //constructor without id
    public Events(String name, String eventDate, String eventStartTime,
                  String eventEndTime, String description, int groupid) {
        this.name = name;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.description = description;
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
    public String getEventDate(){
        return this.eventDate;
    }

    public String getDescription(){
        return this.description;
    }

    //get eventStartTime
    public String getEventStartTime(){
        return this.eventStartTime;
    }

    //get eventEndTime
    public String getEventEndTime(){
        return this.eventEndTime;
    }

    public int getGroupid(){
        return this.groupid;
    }

    public boolean getFound(){
        return this.found;
    }
}
