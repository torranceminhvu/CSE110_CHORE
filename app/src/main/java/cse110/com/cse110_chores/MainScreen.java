package cse110.com.cse110_chores;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SpinnerAdapter;

public class MainScreen extends AppCompatActivity {
    //todo: display group name by getting intent    DONE!!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent get = getIntent();
        // gets the group name from login page
        final int groupid = get.getIntExtra("GROUPID", 0);

        // display group name
        setTitle(get.getStringExtra("groupText"));

        setContentView(R.layout.activity_main_screen);

        // links the button to go to the chore screen
        Button choreButton = (Button) findViewById(R.id.choreButton);

        // on click, it will open the chore list activity
        choreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChoresList.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);

            }
        });

        // links the button to go to the members screen
        Button memberButton = (Button) findViewById(R.id.memberButton);

        // on click, it will open the member list activity
        memberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemberList.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);

            }
        });

        // links the button to go to the payments screen
        Button payButton = (Button) findViewById(R.id.payButton);

        // on click, it will open the payment list activity
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PaymentList.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);

            }
        });

        // links the button to go to the calendar
        Button calendarButton = (Button) findViewById(R.id.calendarButton);

        // on clicks, opens a vew of the calendar page
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarApi.class);
                //intent.putExtras("GROUPID", groupid);
                startActivityForResult(intent, 0);
            }

        });
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
                // startActivity(new Intent(this, MainScreen.class));
                break;
            case R.id.menu_logout:
                startActivity(new Intent(this, CreateSearch_Group.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
