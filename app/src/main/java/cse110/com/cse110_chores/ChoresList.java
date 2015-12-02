package cse110.com.cse110_chores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.AlertDialog.*;

/**
 * Created by Bailey
 */
public class ChoresList extends AppCompatActivity {

    DatabaseHandler db; // access our database
    private ListView choreList; // listview layout where our items go
    ArrayList<String> stringAL = new ArrayList<String>(); // holds a copy of the names
    ArrayList<Chores> choreAL = new ArrayList<Chores>(); // stores the chores
    ArrayList<Names> namesAL = new ArrayList<Names>(); // stores the names
    ArrayList<Names> membersAL = new ArrayList<Names>(); // for populating spinner
    Assigner assigner; // access our assigner class to assign chores
    Chores current; // keeps track of the current chore
    String chore; // grabs the chore name and stores in string
    String frequency; // grabs the frequency and stores in string
    String display; // grabs the name to save a copy to the stringAL

    // keeping track of days.
    Calendar calendar;
    int day;

    // stores the association between chores and names
    ArrayList<ChoreName> choreNameCheck = new ArrayList<ChoreName>();
    // for populating the pop up window sort
    ArrayList<String> sortedChoresList = new ArrayList<String>();

    // creates a spinner object to sort the chores by name
    Spinner spinner;
    // to check what was selected in the spinner
    String sortedBy = "";

    // adapter used to set the chore list screen
    ChoreListAdapter choreAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        // creates a database to obtain data from database
        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);

        // links the listview
        choreList = (ListView) findViewById(R.id.chorelistview);

        // makes a new assigner to assign chore later
        assigner = new Assigner(db, groupid);
        // updates the assigner every time the activity is opened
        assigner.update();

        // gets all the names' index to check later
        namesAL = assigner.getAllIndex();

        // gets all the chores that are linked with names
        choreNameCheck = db.getAllChoreNames(groupid);

        // button used move to the activity to add chores
        final Button choreAddButton = (Button) findViewById(R.id.choreAddButton);
        choreAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starts a new activity chore screen to add the chore
                Intent intent = new Intent(v.getContext(), ChoresScreen.class);
                intent.putExtra("GROUPID", groupid);
                assigner.unassign();

                // closes the activity after chore is done being added
                finish();
                startActivity(intent);
                return;
            }
        });


        // checks whether there are any members assigned yet
        if (namesAL.size() == 0) {
            choreAL = db.getAllChores(groupid);
                for (int i = 0; i < choreAL.size(); i++) {
                    current = choreAL.get(i);
                    chore = current.getName();
                    frequency = current.getFrequency();
                    display = chore + frequency;
                    stringAL.add(display);
            }
        } else {
            choreAL = db.getAllChores(groupid);
            for (int i = 0; i < choreAL.size(); i++) {
                display = choreAL.get(i).getName();
                stringAL.add(display);
            }
        }

        // Button to assign chore
        Button assignChore = (Button) findViewById(R.id.assignChore);
        assignChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // grabs all the chores from the database
                choreAL = db.getAllChores(groupid);
                // assigns the chores
                assigner.assign();
                // gets all the names' as indices
                namesAL = assigner.getAllIndex();

                // for getting the first day set and then comparing with the curr day to increment
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_YEAR);

                // Checks whether there are names to assign before assigning the chores
                if (namesAL.size() != 0){
                    stringAL.clear();
                    for (int i = 0; i < choreAL.size(); i++) {
                        display = namesAL.get(i/namesAL.size()).getName();
                        stringAL.add(display);
                    }
                    // Grabs the updated chores and resets the activity to show updates
                    choreNameCheck = db.getAllChoreNames(groupid);
                    choreAdapter = new ChoreListAdapter(ChoresList.this,
                            R.layout.chores_list_row, stringAL,
                            choreAL, namesAL, choreNameCheck, db, groupid);
                    choreList.setAdapter(choreAdapter);
                }
            }
        });

        // Sets the spinner to hold the names of all the members so they can find all their chores
        spinner = (Spinner) findViewById(R.id.sortSpinner);

        // puts all the member names into a string array
        membersAL = db.getAllNames(groupid);
        final String[] spinnerList = new String[(membersAL.size() + 1)];
        for (int i = 0; i < (membersAL.size() + 1); i++) {
            if(i == 0) {
                spinnerList[i] = "[Sort by...]";
            }
            else {
                spinnerList[i] = membersAL.get(i-1).getName();
            }
        }

        // creates a list of names to populate the edit name
        final String[] listOfNames = new String[membersAL.size()];
        for (int i = 0; i < membersAL.size(); i++) {
            listOfNames[i] = membersAL.get(i).getName();
        }

        // Enables the listview to be clickable so that user can edit the name of who does the chore
        choreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // creates a pop up
                AlertDialog.Builder editNameBuilder = new AlertDialog.Builder(ChoresList.this);
                // sets the title of the pop up to "Choose a member"
                editNameBuilder.setTitle("Choose a member");
                // creates a list of the members and allows them to be selected to change the name
                // of who does the chore
                editNameBuilder.setSingleChoiceItems(listOfNames, -1, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ChoresList.this, listOfNames[which],
                                Toast.LENGTH_SHORT).show();
                        choreList.setTag(new Integer(which));
                    }
                });
                // confirms the change and updates the activity and the database
                editNameBuilder.setPositiveButton("Confirm", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer selected = (Integer) choreList.getTag();
                        choreNameCheck.get(position).seti(selected);
                        db.updateChoreName(choreNameCheck.get(position));
                        choreNameCheck = db.getAllChoreNames(groupid);
                        choreAdapter = new ChoreListAdapter(ChoresList.this,
                                R.layout.chores_list_row, stringAL,
                                choreAL, namesAL, choreNameCheck, db, groupid);
                        choreList.setAdapter(choreAdapter);
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0,0);
                    }
                });
                // cancels the change
                editNameBuilder.setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // creates and shows the pop up
                editNameBuilder.create().show();
            }
        });

        // sets the spinner to hold all the names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // sorts the chore according to the names
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // saves the name selected in spinner to sort the chores by who does the chores
                sortedBy = parent.getItemAtPosition(position).toString();

                // finds the index of the name picked
                namesAL = db.getAllNames(groupid);

                // checks whether there are members to sort the names by
                if (namesAL.size() != 0) {
                    int namePos = 0;
                    foundName:
                    for (; namePos < namesAL.size(); namePos++) {
                        if (sortedBy.equals(namesAL.get(namePos).getName()))
                            break foundName;
                    }
                    // gets the updated choreName list
                    choreNameCheck = db.getAllChoreNames(groupid);
                    if (choreNameCheck.size() != 0) {
                        sortedChoresList.clear();
                        for (int i = 0; i < choreNameCheck.size(); i++) {
                            if (namePos == choreNameCheck.get(i).geti()) {
                                sortedChoresList.add(choreNameCheck.get(i).getChoreName());
                            }
                        }
                    }
                }
                // starts up pop up sorted chores if a name has been selected
                if (!sortedBy.equals("[Sort by...]")) {

                    // to show the list of chores that the selected person is doing
                    AlertDialog.Builder sortedChoresBuilder =
                            new AlertDialog.Builder(ChoresList.this);
                    LayoutInflater sortedChoresInflater = getLayoutInflater();
                    View sortedChoresView = (View)
                            sortedChoresInflater.inflate(R.layout.sorted_chores_list, null);

                    // sets up the view, the title, and the close button
                    sortedChoresBuilder.setView(sortedChoresView);
                    sortedChoresBuilder.setTitle(sortedBy + "'s chores");
                    sortedChoresBuilder.setNegativeButton("Close", new OnClickListener() {
                        // resets the spinner to [Sorted by...] so that reselectiong of the
                        // same name would work
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            spinner.setSelection(0);
                        }
                    });

                    // used to display the chores
                    ListView sortedChoresListView = (ListView)
                            sortedChoresView.findViewById(R.id.sortedChoresList);
                    ArrayAdapter<String> sortedChoresAdapter = new ArrayAdapter<String>(ChoresList.this,
                            android.R.layout.simple_list_item_1, sortedChoresList);
                    sortedChoresListView.setAdapter(sortedChoresAdapter);
                    sortedChoresBuilder.create().show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        Just a reminded what parameters are needed
        private ChoreListAdapter(Context context, int resource, List<String> stringAL,
                List<Chores> choreAL, List<Names> namesAL,
                List<ChoreName> choreNameCheck, DatabaseHandler db )*/
        choreAdapter = new ChoreListAdapter(ChoresList.this, R.layout.chores_list_row, stringAL,
                choreAL, namesAL, choreNameCheck, db, groupid);
        choreList.setAdapter(choreAdapter);
    }


    // creates a menu drop down
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    // On the selected item on the menu, it will switch the activities as appropriate
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
                finish();
                break;
            case R.id.menu_logout:
                finish();
                startActivity(new Intent(this, CreateSearch_Group.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}