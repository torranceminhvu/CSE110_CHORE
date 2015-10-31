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

    //empty constructor
    public Events(){

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
}
