package cse110.com.cse110_chores;

import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matthew on 11/14/2015.
 */
public class    Assigner {
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
        Log.e("NAMESAL", String.valueOf(nameAL.size()));
        Log.e("ChorenameAL", String.valueOf(choreNameAL.size()));
        if (total == 0){
            return;
        }
        if (choreNameCheck.size() != 0){
            for (int i = 0; i < choreNameCheck.size(); i++){
                Log.e("DELETELOOP", String.valueOf(i));
                db.deleteChoreName(choreNameCheck.get(i));
            }
        }

        for (int i = 0; i <choreAL.size(); i++){
            int j = i;
            if (j >= total) {
                j = i - total;
            }
            ChoreName current = new ChoreName(choreAL.get(i).getName(), 0, groupid);
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
