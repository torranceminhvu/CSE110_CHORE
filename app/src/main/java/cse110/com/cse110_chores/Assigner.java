package cse110.com.cse110_chores;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Assigns chores to names in database
 */
public class    Assigner {
    //vars
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    ArrayList<Names> nameAL = new ArrayList<Names>();
    ArrayList<ChoreName> choreNameAL = new ArrayList<ChoreName>();
    ArrayList<ChoreName> choreNameCheck = new ArrayList<ChoreName>();
    DatabaseHandler db;
    Parser parser = new Parser();
    int groupid;
    Calendar cal = Calendar.getInstance();

    //constructor
    public Assigner (DatabaseHandler db, int groupid) {
        this.db = db;
        this.groupid = groupid;
    }

    // removes all choreName from database
    public void unassign(){
        choreNameAL = db.getAllChoreNames(groupid);
        for (int i = 0; i < choreNameAL.size(); i++){
            db.deleteChoreName(choreNameAL.get(i));
        }
    }

    //updates  the name for each chore if the count > freq
    public void update(){
        // vars
        int day = cal.get(Calendar.DAY_OF_YEAR);
        int daysPassed;
        int freq;
        int count;
        int index;

        //retrieve all choreName and name in database
        choreNameAL = db.getAllChoreNames(groupid);
        nameAL = db.getAllNames(groupid);

        int total = nameAL.size();

        /* loop through choreName in database and update the count and set the new name index if
         count > freq
        */
        for (int i = 0; i < choreNameAL.size(); i++){
            daysPassed = day - choreNameAL.get(i).getStartTime();
            freq = choreNameAL.get(i).getFrequency();
            count = choreNameAL.get(i).getCounter();

            for (int j = 0; j < daysPassed; j++){
                if (count < freq - 1){
                    count++;
                }
                else {
                    count = 0;
                    index = choreNameAL.get(i).geti();
                    index++;
                    if (index == total){
                        index = 0;
                    }
                    choreNameAL.get(i).seti(index);
                }
            }
            // set the new time last updated and counter for each chorename and update databse
            choreNameAL.get(i).setCounter(count);
            choreNameAL.get(i).setStartTime(day);
            db.updateChoreName(choreNameAL.get(i));
        }
    }

    // assigns the names to each chore
    public void assign(){
        //vars
        choreAL = db.getAllChores(groupid);
        nameAL = db.getAllNames(groupid);
        choreNameCheck = db.getAllChoreNames(groupid);
        int total = nameAL.size();
        int day = cal.get(Calendar.DAY_OF_YEAR);

        // checks if there are names in database
        if (total == 0){
            return;
        }

        // if there are Chorenames then remove all Chorenames in the database
        if (choreNameCheck.size() != 0){
            for (int i = 0; i < choreNameCheck.size(); i++){
                db.deleteChoreName(choreNameCheck.get(i));
            }
        }

        /* loop through the list of chores in database and pick a name for each chore from
           name database
         */
        for (int i = 0; i <choreAL.size(); i++){
            int j = i;
            if (j >= total) {
                j = i - total;
            }
            // create a new chorename and set its name index.
            ChoreName current = new ChoreName(choreAL.get(i).getName(), 0,
                    parser.parse(choreAL.get(i)), day, groupid);
            current.seti(j);

            // add chorename to database
            db.addChoreName(current);
        }
    }

    // return the name based on the index provided
    public Names getIndex(int index) {
        nameAL = db.getAllNames(groupid);

        Names name = nameAL.get(index);
        return name;
    }

    // returns a name array that matches the chorename based on the index for each chorename
    public ArrayList<Names> getAllIndex(){
        // vars
        ArrayList<Names> result = new ArrayList<Names>();
        choreNameAL = db.getAllChoreNames(groupid);

        //loops through and add names matching chorename based on its index to a name array list
        for (int i = 0; i < choreNameAL.size(); i++){
            int index = choreNameAL.get(i).geti();
            Names current = this.getIndex(index);
            result.add(current);
        }
        return result;
    }

}
