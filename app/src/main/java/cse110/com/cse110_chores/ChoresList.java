package cse110.com.cse110_chores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static android.app.AlertDialog.*;


public class ChoresList extends AppCompatActivity {

    DatabaseHandler db; // access our database
    private ListView choreList; // listview layout where our items go
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Chores> choreAL = new ArrayList<Chores>(); // stores the chores
    ArrayList<Names> namesAL = new ArrayList<Names>(); // stores the names
    ArrayList<Names> membersAL = new ArrayList<Names>(); // for populating spinner
    Assigner assigner;
    Chores current;
    String chore;
    String frequency;
    String display;

    // keeping track of days.
    Calendar calendar;
    int day;
    int currDay;

    // stores the association between chores and names
    ArrayList<ChoreName> choreNameCheck = new ArrayList<ChoreName>();
    // for populating the pop up window sort
    ArrayList<String> sortedChoresList = new ArrayList<String>();

    Spinner spinner;
    String sortedBy = "";

    ChoreListAdapter choreAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        choreList = (ListView) findViewById(R.id.chorelistview);
        assigner = new Assigner(db, groupid);
        assigner.update();
        namesAL = assigner.getAllIndex();
        choreNameCheck = db.getAllChoreNames(groupid);

        final Button choreAddButton = (Button) findViewById(R.id.choreAddButton);

        choreAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChoresScreen.class);
                intent.putExtra("GROUPID", groupid);
                assigner.unassign();

                finish();
                startActivity(intent);
                return;
            }
        });


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

        // Added button to assign chore
        Button assignChore = (Button) findViewById(R.id.assignChore);

        assignChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choreAL = db.getAllChores(groupid);
                assigner.assign();
                namesAL = assigner.getAllIndex();

                // for getting the first day set and then comparing with the curr day to increment
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_YEAR);

                //Log.e("NAMESAL", String.valueOf(namesAL.size()));
                if (namesAL.size() != 0){
                    stringAL.clear();
                    for (int i = 0; i < choreAL.size(); i++) {
                        display = namesAL.get(i/namesAL.size()).getName();
                        stringAL.add(display);
                    }
                    choreNameCheck = db.getAllChoreNames(groupid);
                    choreAdapter = new ChoreListAdapter(ChoresList.this, R.layout.chores_list_row, stringAL,
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

        final String[] listOfNames = new String[membersAL.size()];
        for (int i = 0; i < membersAL.size(); i++) {
            listOfNames[i] = membersAL.get(i).getName();
        }

        choreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChoresList.this);
                builder.setTitle("Choose a member");
                builder.setSingleChoiceItems(listOfNames, -1, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ChoresList.this, listOfNames[which], Toast.LENGTH_SHORT).show();
                        choreList.setTag(new Integer(which));
                    }
                });
                builder.setPositiveButton("Confirm", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer selected = (Integer) choreList.getTag();
                        //String oldName = namesAL.get(position/namesAL.size()).getName();
                        //String choreName = choreAL.get(position).getName();
                        //ChoreName tempChoreName = db.getChoreName(oldName, groupid);
                        //tempChoreName.seti(selected);
                        choreNameCheck.get(position).seti(selected);
                        choreAdapter = new ChoreListAdapter(ChoresList.this, R.layout.chores_list_row, stringAL,
                                choreAL, namesAL, choreNameCheck, db, groupid);
                        choreList.setAdapter(choreAdapter);
                    }
                });
                builder.setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });

        // sets the spinner to hold all the names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // sorts the chore according to the names
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sortedBy = parent.getItemAtPosition(position).toString();

                // finds the index of the name picked
                namesAL = assigner.getAllIndex();

                //Log.e("NAMESAL", String.valueOf(namesAL.size()));
                if (namesAL.size() != 0) {
                    int namePos = 0;
                    foundName:
                    for (; namePos < namesAL.size(); namePos++) {
                        if (sortedBy.equals(namesAL.get(namePos).getName()))
                            break foundName;
                    }
                    choreNameCheck = db.getAllChoreNames(groupid);
                    if (choreNameCheck.size() != 0) {
                        for (int i = 0; i < choreNameCheck.size(); i++) {
                            if (namePos == choreNameCheck.get(i).geti()) {
                                sortedChoresList.add(choreNameCheck.get(i).getChoreName());
                            }
                        }
                    }
                }
                // starts up pop up sorted chores
                if (!sortedBy.equals("[Sort by...]")) {
                    Intent sortedChoresListIntent = new Intent(ChoresList.this, PopUpSortedChores.class);
                    sortedChoresListIntent.putStringArrayListExtra("sortedChoresList", sortedChoresList);
                    startActivity(sortedChoresListIntent);
                    sortedChoresList.clear();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        private ChoreListAdapter(Context context, int resource, List<String> stringAL,
                List<Chores> choreAL, List<Names> namesAL,
                List<ChoreName> choreNameCheck, DatabaseHandler db )*/
        choreAdapter = new ChoreListAdapter(ChoresList.this, R.layout.chores_list_row, stringAL,
                choreAL, namesAL, choreNameCheck, db, groupid);
        choreList.setAdapter(choreAdapter);
    }

    /*
    // custom adapter to display in listview
    private class myAdapter extends ArrayAdapter<String> {
        private int layout;
        private myAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder mainViewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder();
                myViewHolder.frequencyTextView = (TextView) convertView.findViewById(R.id.frequencyTextView);
                myViewHolder.choreTextView = (TextView) convertView.findViewById(R.id.choreTextView);
                myViewHolder.personTextView = (TextView) convertView.findViewById(R.id.personTextView);
                myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
                myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteChore(choreAL.get(position));
                        choreAL.remove(position);
                        stringAL.remove(position);
                        namesAL.remove(position);
                        db.deleteChoreName(choreNameCheck.get(position));
                        theadapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(myViewHolder);
                myViewHolder.frequencyTextView.setText("Frequency: " + choreAL.get(position).getFrequency());
                myViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
                myViewHolder.personTextView.setText("");
                if (namesAL.size() != 0) {
                    myViewHolder.personTextView.setText("Name: " + namesAL.get(position).getName());
                }
            } else {
                mainViewHolder = (MyViewHolder) convertView.getTag();
                mainViewHolder.frequencyTextView.setText("Frequency: " + choreAL.get(position).getFrequency());
                mainViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
                mainViewHolder.personTextView.setText("");
                if (namesAL.size() != 0) {
                    mainViewHolder.personTextView.setText("Name: " + namesAL.get(position).getName());
                }
            }
            return convertView;
        }
    }
    public class MyViewHolder {
        TextView frequencyTextView;
        TextView choreTextView;
        TextView personTextView;
        Button delete;
    }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

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