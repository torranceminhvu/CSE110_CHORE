package cse110.com.cse110_chores;

import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 11/14/2015.
 */
public class    Assigner {
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    ArrayList<Names> nameAL = new ArrayList<Names>();
    ArrayList<ChoreName> choreNameAL = new ArrayList<ChoreName>();
    ArrayList<ChoreName> choreNameCheck = new ArrayList<ChoreName>();
    DatabaseHandler db;
    Parser parser = new Parser();
    int groupid;
    Calendar cal = Calendar.getInstance();

    public Assigner (DatabaseHandler db, int groupid) {
        this.db = db;
        this.groupid = groupid;
    }

    public void update(){
        int day = cal.DAY_OF_YEAR;
        int daysPassed;
        int freq;
        int count;
        int index;
        choreNameAL = db.getAllChoreNames(groupid);
        nameAL = db.getAllNames(groupid);
        int total = nameAL.size();

        for (int i = 0; i < choreNameAL.size(); i++){
            daysPassed = day - choreNameAL.get(i).getStartTime();
            freq = choreNameAL.get(i).getFrequency();
            count = choreNameAL.get(i).getCounter();

            for (int j = 0; j < daysPassed; j++){
                if (count < freq){
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
            choreNameAL.get(i).setCounter(count);
            choreNameAL.get(i).setStartTime(day - count);

            db.updateChoreName(choreNameAL.get(i));
        }
    }

    public void assign(){
        choreAL = db.getAllChores(groupid);
        nameAL = db.getAllNames(groupid);
        choreNameCheck = db.getAllChoreNames(groupid);
        int total = nameAL.size();
        int day = cal.DAY_OF_YEAR;

        if (total == 0){
            return;
        }
        if (choreNameCheck.size() != 0){
            for (int i = 0; i < choreNameCheck.size(); i++){
                db.deleteChoreName(choreNameCheck.get(i));
            }
        }

        for (int i = 0; i <choreAL.size(); i++){
            int j = i;
            if (j >= total) {
                j = i - total;
            }
            ChoreName current = new ChoreName(choreAL.get(i).getName(), 0, parser.parse(choreAL.get(i)), day, groupid);
            current.seti(j);

            db.addChoreName(current);
        }
    }

    public Names getIndex(int index) {
        nameAL = db.getAllNames(groupid);

        Names name = nameAL.get(index);
        return name;
    }

    public ArrayList<Names> getAllIndex(){
        ArrayList<Names> result = new ArrayList<Names>();
        choreNameAL = db.getAllChoreNames(groupid);

        for (int i = 0; i < choreNameAL.size(); i++){
            int index = choreNameAL.get(i).geti();
            //Log.e("index", String.valueOf(index));
            Names current = this.getIndex(index);
            result.add(current);
        }
        return result;
    }

    public ArrayList<Chores> getChoreAL(){
        return this.choreAL;
    }
}
