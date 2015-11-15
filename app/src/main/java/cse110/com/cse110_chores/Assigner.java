package cse110.com.cse110_chores;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Matthew on 11/14/2015.
 */
public class Assigner {
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    ArrayList<Names> nameAL = new ArrayList<Names>();
    ArrayList<ChoreName> choreNameAL = new ArrayList<ChoreName>();
    ArrayList<ChoreName> choreNameCheck = new ArrayList<ChoreName>();
    DatabaseHandler db;
    int groupid;

    public Assigner (DatabaseHandler db, int groupid) {
        this.db = db;
        this.groupid = groupid;
    }

    public void assign(){
        choreAL = db.getAllChores(groupid);
        nameAL = db.getAllNames(groupid);
        choreNameCheck = db.getAllChoreNames(groupid);
        int total = nameAL.size();

        if (choreNameCheck.size() != 0){
            for (int i = 0; i < choreNameCheck.size(); i++){
                db.deleteChoreName(choreNameCheck.get(i));
            }
        }

        for (int i = 0; i <choreAL.size(); i++){
            int j = i;
            if (j > total) {
                j = i - total;
            }
            ChoreName current = new ChoreName(choreAL.get(i).getName(), 0, groupid);
            current.seti(j);

            choreNameAL.add(current);
        }

        for (int i = 0; i < choreNameAL.size(); i++){
            db.addChoreName(choreNameAL.get(i));
        }
    }

    public Names getIndex(int index) {
        nameAL = db.getAllNames(groupid);

        Names name = nameAL.get(index);
        return name;
    }

    public ArrayList<Names> getAllIndex(){
        ArrayList<Names> result = new ArrayList<Names>();
        for (int i = 0; i < choreNameAL.size(); i++){
            int index = choreNameAL.get(i).geti();

            Names current = this.getIndex(index);
            result.add(current);
        }
        return result;
    }

    public ArrayList<Chores> getChoreAL(){
        return this.choreAL;
    }
}
