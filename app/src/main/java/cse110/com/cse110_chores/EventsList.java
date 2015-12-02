package cse110.com.cse110_chores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EventsList extends AppCompatActivity {

    private ListView eventList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Events> eventAL = new ArrayList<Events>();
    DatabaseHandler db;
    Events current;
    String display;

    String event;
    String date;
    String startTime;
    String endTime;
    String description;

    EventListAdapter eventListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        eventList = (ListView) findViewById(R.id.eventListView);

        Button eventAddButton = (Button) findViewById(R.id.eventAddButton);

        eventAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventsScreen.class);
                intent.putExtra("GROUPID", groupid);

                finish();
                startActivity(intent);
                return;
            }
        });

        eventAL = db.getAllEvents(groupid);
        for (int i = 0; i < eventAL.size(); i++){
            current = eventAL.get(i);
            event = current.getName();
            date = current.getEventDate();
            startTime = current.getEventStartTime();
            endTime = current.getEventEndTime();
            display =  event;
            description = current.getDescription();
            stringAL.add(display);
        }

        eventListAdapter = new EventListAdapter(EventsList.this, R.layout.event_list_row,
                stringAL, eventAL, db );
        eventList.setAdapter(eventListAdapter);
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