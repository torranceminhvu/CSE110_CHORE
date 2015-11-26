package cse110.com.cse110_chores;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.Override;
import java.lang.String;
import cse110.com.cse110_chores.R;

/**
 * Created by Bailey
 */
public class ChoresScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHandler db; // enables access to the database
    Spinner spinner; // creates a spinner to select the frequency
    String frequency = ""; // stores the chosen frequency into a string

    // creates a string array to populate the spinner
    String[] spinnerList = {"Choose frequency", "Weekly", "Bi-Weekly", "Daily", "Monthly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_screen);

        Intent get = getIntent();
        // obtains the group information
        final int groupid = get.getIntExtra("GROUPID", 0);
        db = new DatabaseHandler(this);

        // links the spinner in the layout file
        spinner= (Spinner) findViewById(R.id.choreSpinner);

        // creates a string adapter to show the items in the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerList);
        // sets the adapter as a dropdown resource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // sets the adapter
        spinner.setAdapter(adapter);

        // when an item is selected the spinner will do something
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // used to check which frequency was chosen
                frequency = parent.getItemAtPosition(position).toString();
                // shows the user what frequency was chosen
                if (!frequency.equals("Choose frequency"))
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) +
                            " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // link the add chore button
        Button addChore = (Button) findViewById(R.id.addChore);

        // obtains the user input
        final EditText choreText = (EditText) findViewById(R.id.chore);

        addChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {

                // stores the user input into a string
                String chore = choreText.getText().toString();

                // checks whether the frequency was chosen or if the user has typed in a chore name
                if (!frequency.equals("Choose frequency") && chore.length() > 0) {

                    // sends the information back to the chore list activity
                    Intent intent = new Intent(v.getContext(), ChoresList.class);
                    db.addChore(new Chores(chore, frequency, groupid));
                    intent.putExtra("GROUPID", groupid);

                    // closes the current activity
                    finish();
                    startActivity(intent);
                    return;
                }
                else {

                    // lets the user know that they are either missing a frequency or a chore name
                    Toast.makeText(getBaseContext(), "Please select a frequency or enter a chore",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
