package cse110.com.cse110_chores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EventsScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_screen);

        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        db = new DatabaseHandler(this);

        Button addEvent = (Button) findViewById(R.id.addEvent);

        final EditText eventName = (EditText) findViewById(R.id.eventName);
        final EditText eventDate = (EditText) findViewById(R.id.eventDate);
        final EditText eventStartTime = (EditText) findViewById(R.id.eventStartTime);
        final EditText eventEndTime = (EditText) findViewById(R.id.eventEndTime);
        final EditText eventDescription = (EditText) findViewById(R.id.eventDescription);

         addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = eventName.getText().toString();
                String date = eventDate.getText().toString();
                String startTime = eventStartTime.getText().toString();
                String endTime = eventEndTime.getText().toString();
                String description = eventDescription.getText().toString();

                if (event.length() > 0 && date.length() > 0 &&
                        startTime.length() > 0 && endTime.length() > 0
                        && description.length() > 0) {
                    Intent intent = new Intent(v.getContext(), EventsList.class);

                    db.addEvent(new Events (event, date, startTime, endTime, description, groupid));

                    intent.putExtra("GROUPID", groupid);

                    finish();
                    startActivity(intent);
                    return;
                }
                else {
                    Toast.makeText(EventsScreen.this, "Please fill in all text fields",
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

