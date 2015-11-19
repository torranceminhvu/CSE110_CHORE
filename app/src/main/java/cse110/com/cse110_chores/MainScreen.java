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
        final int groupid = get.getIntExtra("GROUPID", 0);

        // display group name
        setTitle(get.getStringExtra("groupText"));

        setContentView(R.layout.activity_main_screen);



        //action.getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setTitle(groupid);
        Button chorebutton = (Button) findViewById(R.id.choreButton);

        chorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChoresList.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);

            }
        });

        Button memberbutton = (Button) findViewById(R.id.memberButton);

        memberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemberList.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);

            }
        });

        Button paybutton = (Button) findViewById(R.id.payButton);

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PaymentList.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);

            }
        });

        Button calendarButton = (Button) findViewById(R.id.calendarButton);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarApi.class);
                //intent.putExtras("GROUPID", groupid);
                startActivityForResult(intent, 0);
            }

        });
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
                startActivity(new Intent(this, MainScreen.class));
                break;
            case R.id.menu_logout:
                startActivity(new Intent(this, CreateSearch_Group.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
