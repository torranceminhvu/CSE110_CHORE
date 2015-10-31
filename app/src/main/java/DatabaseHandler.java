import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 10/29/2015.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    //db version
    private static final int DATABASE_VERSION = 1;

    //db name
    private static final String DATABASE_NAME = "Tables";

    //Groups table name
    private static final String TABLE_GROUPS = "Groups";

    //Chores table name
    private static final String TABLE_CHORES = "Chores";

    //Events table name
    private static final String TABLE_EVENTS = "Events";

    //Name table name
    private static final String TABLE_NAMES = "Names";

    //id column name
    private static final String KEY_ID = "id";


    //Groups table column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    //Chores table column names
    private static final String KEY_CHORENAME = "choreName";
    private static final String KEY_FREQUENCY = "frequency";
    private static final String KEY_CHORESGROUPS = "choresGroups";

    //Events table column names
    private static final String KEY_EVENTNAME = "eventName";
    private static final String KEY_EVENTSTARTDATE = "eventStartDate";
    private static final String KEY_EVENTENDDATE = "eventEndDate";
    private static final String KEY_EVENTSTARTTIME = "eventStartTime";
    private static final String KEY_EVENTENDTIME = "eventEndTime";
    private static final String KEY_EVENTSGROUPS = "eventsGroups";

    //Names table column names
    private static final String KEY_NAME = "name";
    private static final String KEY_NAMESGROUPS = "nameGroups";

    //constructor
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ");";

        String CREATE_CHORES_TABLE = "CREATE TABLE " + TABLE_CHORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CHORENAME + " TEXT,"
                + KEY_FREQUENCY + " TEXT, " + KEY_CHORESGROUPS + " TEXT, "
                + "FOREIGN KEY(" + KEY_CHORESGROUPS + ") REFERENCES " + TABLE_GROUPS
                + "(" + KEY_ID + "));";

        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EVENTNAME + " TEXT,"
                + KEY_EVENTSTARTDATE + " TEXT," + KEY_EVENTENDDATE + " TEXT,"
                + KEY_EVENTSTARTTIME + " TEXT," + KEY_EVENTENDTIME + " TEXT, "
                + KEY_EVENTSGROUPS + " TEXT, " + KEY_CHORESGROUPS + " TEXT, "
                + "FOREIGN KEY(" + KEY_EVENTSGROUPS + ") REFERENCES " + TABLE_GROUPS
                + "(" + KEY_ID + "));";

        String CREATE_NAMES_TABLE = "CREATE TABLE " + TABLE_NAMES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, "
                + KEY_NAMESGROUPS + " TEXT, " + KEY_CHORESGROUPS + " TEXT, "
                + "FOREIGN KEY(" + KEY_NAMESGROUPS + ") REFERENCES " + TABLE_GROUPS
                + "(" + KEY_ID + "));";

        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_CHORES_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_NAMES_TABLE);
    }

    //upgrade db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //drop old table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);

        //create table again
        onCreate(db);
    }

    //CRUD
    //Adding new groups
    public void addGroup(Groups group) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, group.getUsername());
        values.put(KEY_PASSWORD, group.getPassword());

        // Inserting Row
        db.insert(TABLE_GROUPS, null, values);
        db.close(); // Closing database connection
    }

    //Reading groups
    public Groups getGroup(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[] { KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD }, KEY_USERNAME + "=?",
                new String[] { username }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Groups(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
    }

    //Deleting groups
    public void deleteGroup(Groups group) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS, KEY_ID + " = ?",
                new String[] { String.valueOf(group.getId()) });
        db.close();
    }



    //Adding new chores
    public void addChore(Chores chore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHORENAME, chore.getName());
        values.put(KEY_FREQUENCY, chore.getFrequency());
        values.put(KEY_CHORESGROUPS, chore.getGroupid());

        // Inserting Row
        db.insert(TABLE_CHORES, null, values);
        db.close(); // Closing database connection
    }

    //Reading chores
    public Chores getChore(String chorename, int groupid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CHORES + " WHERE "
                + KEY_CHORENAME + " = " + chorename + " AND " + KEY_CHORESGROUPS
                + " = " + groupid;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return new Chores(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
    }

    //Get all chores
    public List<Chores> getAllChores(int groupid){
        List<Chores> chores = new ArrayList<Chores>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CHORES + " WHERE "
                + KEY_CHORESGROUPS + " = " + groupid;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Chores chore = new Chores(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)));

                chores.add(chore);
            }while (cursor.moveToNext());
        }

        return chores;
    }

    //Update chores

    //Deleting chores
    public void deleteChore(Chores chore) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHORES, KEY_ID + " = ?",
                new String[] { String.valueOf(chore.getId()) });
        db.close();
    }




    //Adding new events

    //Reading events

    //Updating events

    //Deleting events



    //Adding new names
    public void addName(Names name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHORENAME, name.getName());
        values.put(KEY_NAMESGROUPS, name.getGroupid());

        // Inserting Row
        db.insert(TABLE_NAMES, null, values);
        db.close(); // Closing database connection
    }

    //Reading names
    public Names getName(String name, int groupid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAMES + " WHERE "
                + KEY_NAME + " = " + name + " AND " + KEY_NAMESGROUPS
                + " = " + groupid;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return new Names(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
    }

    //Get all chores
    public List<Names> getAllNames(int groupid){
        List<Names> names = new ArrayList<Names>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAMES + " WHERE "
                + KEY_NAMESGROUPS + " = " + groupid;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Names name = new Names(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)));

                names.add(name);
            }while (cursor.moveToNext());
        }

        return names;
    }

    //Updating names

    //Deleting names
    public void deleteName(Names name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES, KEY_ID + " = ?",
                new String[] { String.valueOf(name.getId()) });
        db.close();
    }

}
