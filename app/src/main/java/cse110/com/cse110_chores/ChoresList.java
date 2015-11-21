package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ChoresList extends AppCompatActivity {

    DatabaseHandler db;
    private ListView choreList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    ArrayList<Names> namesAL = new ArrayList<Names>();
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

    // for populating the pop up window sort
    ArrayList<ChoreName> choreNameCheck = new ArrayList<ChoreName>();
    ArrayList<String> sortedChoresList = new ArrayList<String>();

    Spinner spinner;
    String sortedBy = "";

    myAdapter theadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        choreList = (ListView) findViewById(R.id.chorelistview);

        Button choreAddButton = (Button) findViewById(R.id.choreAddButton);

        choreAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChoresScreen.class);
                intent.putExtra("GROUPID", groupid);

                finish();
                startActivity(intent);
                return;
            }
        });

        assigner = new Assigner(db, groupid);
        namesAL = assigner.getAllIndex();

        if (namesAL.size() == 0) {
            choreAL = db.getAllChores(groupid);
            for (int i = 0; i < choreAL.size(); i++) {
                current = choreAL.get(i);
                chore = current.getName();
                frequency = current.getFrequency();
                display = frequency + ":  " + chore;
                stringAL.add(display);
            }
        } else {
            choreAL = db.getAllChores(groupid);
            for (int i = 0; i < choreAL.size(); i++) {
                current = choreAL.get(i);
                chore = current.getName();
                frequency = current.getFrequency();
                display = frequency + ":  " + chore + "             " + namesAL.get(i).getName();
                //display = frequency + ":  " + chore;
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
                        current = choreAL.get(i);
                        chore = current.getName();
                        frequency = current.getFrequency();
                        display = frequency + ":  " + chore + "             " + namesAL.get(i).getName();
                        stringAL.add(display);
                        theadapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // Sets the spinner to hold the names of all the members so they can find all their chores
        spinner = (Spinner) findViewById(R.id.sortSpinner);

        // puts all the member names into a string array
        membersAL = db.getAllNames(groupid);
        String[] spinnerList = new String[(membersAL.size() + 1)];
        for (int i = 0; i < (membersAL.size() + 1); i++) {
            if(i == 0) {
                spinnerList[i] = "[Sort by...]";
            }
            else {
                spinnerList[i] = membersAL.get(i-1).getName();
            }
        }

        // sets the spinner to hold all the names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
                    if (choreNameCheck.size() != 0){
                        for (int i = 0; i < choreNameCheck.size(); i++) {
                            if ( namePos == choreNameCheck.get(i).geti() ) {
                                sortedChoresList.add(choreNameCheck.get(i).getChoreName());
                            }
                        }
                    }
                }

                // starts up pop up sorted chores
                if ( !sortedBy.equals("[Sort by...]")) {
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

        //Collections.sort(choreAL);

        theadapter = new myAdapter(ChoresList.this, R.layout.chores_list_row, stringAL);

        choreList.setAdapter(theadapter);
    }


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
            if(convertView == null) {
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
                        db.deleteChoreName(choreNameCheck.get(position));
                        theadapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(myViewHolder);
                myViewHolder.frequencyTextView.setText("Frequency: " + choreAL.get(position).getFrequency());
                myViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
                myViewHolder.personTextView.setText("");
            }
            else {
                mainViewHolder = (MyViewHolder) convertView.getTag();
                mainViewHolder.frequencyTextView.setText("Frequency: " + choreAL.get(position).getFrequency());
                mainViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
                mainViewHolder.personTextView.setText("");
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
