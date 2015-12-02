package cse110.com.cse110_chores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 10/29/2015.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    //db version
    private static int DATABASE_VERSION = 8;

    //db name
    private static final String DATABASE_NAME = "Tables";

    //cse110.com.cse110_chores.Groups table name
    private static final String TABLE_GROUPS = "Groups";

    //cse110.com.cse110_chores.Chores table name
    private static final String TABLE_CHORES = "Chores";

    //cse110.com.cse110_chores.Events table name
    private static final String TABLE_EVENTS = "Events";

    //Name table name
    private static final String TABLE_NAMES = "Names";

    //Payment table name
    private static final String TABLE_PAYMENTS = "Payments";

    private static final String TABLE_CHORENAME = "Chorename";

    //id column name
    private static final String KEY_ID = "id";


    //cse110.com.cse110_chores.Groups table column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    //cse110.com.cse110_chores.Chores table column names
    private static final String KEY_CHORENAME = "choreName";
    private static final String KEY_FREQUENCY = "frequency";
    private static final String KEY_CHORESGROUPS = "choresGroups";

    //cse110.com.cse110_chores.Events table column names
    private static final String KEY_EVENTNAME = "eventName";
    private static final String KEY_EVENTDATE = "eventDate";
    private static final String KEY_EVENTDESCRIPTION = "eventDescription";
    private static final String KEY_EVENTSTARTTIME = "eventStartTime";
    private static final String KEY_EVENTENDTIME = "eventEndTime";
    private static final String KEY_EVENTSGROUPS = "eventsGroups";

    //cse110.com.cse110_chores.Names table column names
    private static final String KEY_NAME = "name";
    private static final String KEY_NAMESGROUPS = "nameGroups";

    //Payments tables' columns
    private static final String KEY_OWNER = "owner";
    private static final String KEY_OWNEE = "ownee";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PAYMENTSGROUPS = "paymentsGroups";

    private static final String KEY_CHORE = "chore";
    private static final String KEY_I = "i";
    private static final String KEY_CHORENAMESGROUPS = "choreNamesGroups";
    private static final String KEY_COUNTER = "counter";
    private static final String KEY_FREQ = "freq";
    private static final String KEY_START = "startTime";

    //constructor
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_GROUPS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GROUPS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT);";


        String CREATE_CHORES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CHORES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CHORENAME + " TEXT,"
                + KEY_FREQUENCY + " TEXT, " + KEY_CHORESGROUPS + " TEXT, "
                + "FOREIGN KEY(" + KEY_CHORESGROUPS + ") REFERENCES " + TABLE_GROUPS
                + "(" + KEY_ID + "));";

        String CREATE_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EVENTNAME + " TEXT,"
                + KEY_EVENTDATE + " TEXT,"
                + KEY_EVENTSTARTTIME + " TEXT," + KEY_EVENTENDTIME + " TEXT, "
                + KEY_EVENTDESCRIPTION + " TEXT, "
                + KEY_EVENTSGROUPS + " TEXT, " + "FOREIGN KEY(" + KEY_EVENTSGROUPS + ") REFERENCES "
                + TABLE_GROUPS + "(" + KEY_ID + "));";

        String CREATE_NAMES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAMES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT, "
                + KEY_NAMESGROUPS + " TEXT, " + "FOREIGN KEY(" + KEY_NAMESGROUPS + ") REFERENCES "
                + TABLE_GROUPS + "(" + KEY_ID + "));";

        String CREATE_PAYMENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PAYMENTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OWNER + " TEXT, "
                + KEY_OWNEE + " TEXT, " + KEY_AMOUNT + " TEXT, " + KEY_DESCRIPTION + " TEXT, "
                + KEY_PAYMENTSGROUPS + " TEXT, " + "FOREIGN KEY(" + KEY_PAYMENTSGROUPS + ") REFERENCES "
                + TABLE_GROUPS + "(" + KEY_ID + "));";

        String CREATE_CHORENAMES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CHORENAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CHORE + " TEXT, " + KEY_I + " TEXT, "
                + KEY_COUNTER + " TEXT, " + KEY_FREQ + " TEXT, " + KEY_START + " TEXT, "
                + KEY_CHORENAMESGROUPS + " TEXT, " + "FOREIGN KEY(" + KEY_CHORENAMESGROUPS + ") REFERENCES "
                + TABLE_GROUPS + "(" + KEY_ID + "));";

        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_CHORES_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_NAMES_TABLE);
        db.execSQL(CREATE_PAYMENTS_TABLE);
        db.execSQL(CREATE_CHORENAMES_TABLE);
    }

    //upgrade db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //drop old table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHORENAME);

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

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROUPS + " WHERE " + KEY_USERNAME
                + " = ?", new String[]{username});

        if(cursor != null && cursor.moveToFirst()) {
            return new Groups(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
        }
        else
            return new Groups();
    }

    //Deleting groups
    public void deleteGroup(Groups group) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS, KEY_ID + " = ?",
                new String[]{String.valueOf(group.getId())});
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

        Cursor cursor = db.rawQuery("SELECT " + KEY_CHORENAME + ", " + KEY_CHORESGROUPS + " FROM "
                + TABLE_CHORES + " WHERE " + KEY_CHORENAME + " = ? AND " + KEY_CHORESGROUPS
                + " = ?", new String[]{chorename, String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            return new Chores(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)));
        }
        else
            return new Chores();
    }

    //Get all chores
    public ArrayList<Chores> getAllChores(int groupid){
        ArrayList<Chores> chores = new ArrayList<Chores>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHORES + " WHERE " + KEY_CHORESGROUPS
                + " = ?", new String[]{String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            do{
                Chores chore = new Chores(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)));

                chores.add(chore);
            }while (cursor.moveToNext());
        }

        db.close();
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
    public void addEvent(Events event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENTNAME, event.getName());
        values.put(KEY_EVENTDATE, event.getEventDate());
        values.put(KEY_EVENTSTARTTIME, event.getEventStartTime());
        values.put(KEY_EVENTENDTIME, event.getEventEndTime());
        values.put(KEY_EVENTDESCRIPTION, event.getDescription());
        values.put(KEY_EVENTSGROUPS, event.getGroupid());

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        db.close(); // Closing database connection
    }

    //Get all events
    public ArrayList<Events> getAllEvents(int groupid) {
        ArrayList<Events> events = new ArrayList<Events>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTSGROUPS
                + " = ?", new String[]{String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Events event = new Events(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)));

                events.add(event);
            } while (cursor.moveToNext());
        }

        db.close();
        return events;
    }


    //Deleting events
    public void deleteEvent(Events event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }


    //Adding new names
    public void addName(Names name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name.getName());
        values.put(KEY_NAMESGROUPS, name.getGroupid());

        // Inserting Row
        db.insert(TABLE_NAMES, null, values);
        db.close(); // Closing database connection
    }

    //Reading names
    public Names getName(String name, int groupid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + KEY_NAME + ", " + KEY_NAMESGROUPS + " FROM "
                + TABLE_NAMES + " WHERE " + KEY_NAME + " = ? AND " + KEY_NAMESGROUPS
                + " = ?", new String[]{name, String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            return new Names(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        }
        else
            return new Names();
    }

    //Get all chores
    public ArrayList<Names> getAllNames(int groupid){
        ArrayList<Names> names = new ArrayList<Names>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAMES + " WHERE " + KEY_NAMESGROUPS
                + " = ?", new String[]{String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            do{
                Names name = new Names(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)));

                names.add(name);
            }while (cursor.moveToNext());
        }

        db.close();
        return names;
    }

    //Deleting names
    public void deleteName(Names name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES, KEY_ID + " = ?",
                new String[]{String.valueOf(name.getId())});
        db.close();
    }



    //Adding new payments
    public void addPayment(Payments payment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OWNER, payment.getOwner());
        values.put(KEY_OWNEE, payment.getOwnee());
        values.put(KEY_AMOUNT, payment.getAmount());
        values.put(KEY_DESCRIPTION, payment.getDescription());
        values.put(KEY_PAYMENTSGROUPS, payment.getGroupid());

        // Inserting Row
        db.insert(TABLE_PAYMENTS, null, values);
        db.close(); // Closing database connection
    }

    //Get all payments
    public ArrayList<Payments> getAllPayments(int groupid){
        ArrayList<Payments> payments = new ArrayList<Payments>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PAYMENTS + " WHERE " + KEY_PAYMENTSGROUPS
                + " = ?", new String[]{String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            do{
                Payments payment = new Payments(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), Integer.parseInt(cursor.getString(5)));

                payments.add(payment);
            }while (cursor.moveToNext());
        }

        db.close();
        return payments;
    }

    //Updating payments

    //Deleting payments
    public void deletePayment(Payments payment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAYMENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(payment.getId()) });
        db.close();
    }


    //Adding new chorenames
    public void addChoreName(ChoreName choreName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHORE, choreName.getChoreName());
        values.put(KEY_I, choreName.geti());
        values.put(KEY_COUNTER, choreName.getCounter());
        values.put(KEY_FREQ, choreName.getFrequency());
        values.put(KEY_START, choreName.getStartTime());
        values.put(KEY_CHORENAMESGROUPS, choreName.getGroupid());

        // Inserting Row
        db.insert(TABLE_CHORENAME, null, values);
        db.close(); // Closing database connection
    }

    //Reading names
    public ChoreName getChoreName(String choreName, int groupid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + KEY_CHORE + ", " + KEY_CHORENAMESGROUPS + " FROM "
                + TABLE_CHORENAME + " WHERE " + KEY_CHORE + " = ? AND " + KEY_CHORENAMESGROUPS
                + " = ?", new String[]{choreName, String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            return new ChoreName(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
        }
        else
            return new ChoreName();
    }

    //Get all chores
    public ArrayList<ChoreName> getAllChoreNames(int groupid){
        ArrayList<ChoreName> ChoreNames = new ArrayList<ChoreName>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHORENAME + " WHERE " + KEY_CHORENAMESGROUPS
                + " = ?", new String[]{String.valueOf(groupid)});

        if (cursor != null && cursor.moveToFirst()){
            do{
                ChoreName choreName = new ChoreName(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));

                ChoreNames.add(choreName);
            }while (cursor.moveToNext());
        }

        db.close();
        return ChoreNames;
    }

    //Updating names
    public void updateChoreName(ChoreName choreName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHORE, choreName.getChoreName());
        values.put(KEY_I, choreName.geti());
        values.put(KEY_COUNTER, choreName.getCounter());
        values.put(KEY_FREQ, choreName.getFrequency());
        values.put(KEY_START, choreName.getStartTime());
        values.put(KEY_CHORENAMESGROUPS, choreName.getGroupid());

        db.update(TABLE_CHORENAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(choreName.getId()) });
        db.close();
    }

    //Deleting names
    public void deleteChoreName(ChoreName choreName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("deleteid", String.valueOf(choreName.getId()));
        db.delete(TABLE_CHORENAME, KEY_ID + " = ?",
                new String[]{String.valueOf(choreName.getId())});
        db.close();
    }
}

