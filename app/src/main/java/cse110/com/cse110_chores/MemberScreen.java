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

import java.lang.Override;import java.lang.String;import cse110.com.cse110_chores.R;

public class MemberScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_screen);

        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        db = new DatabaseHandler(this);

        Button addMember = (Button) findViewById(R.id.addMember);

        final EditText memberText = (EditText) findViewById(R.id.member);

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {

                // stores the user input into a string
                String member = memberText.getText().toString();

                // checks if the user input a member name
                if (member.length() > 0) {
                    Intent intent = new Intent(v.getContext(), MemberList.class);
                    db.addName(new Names(member, groupid));
                    intent.putExtra("GROUPID", groupid);

                    // closes the current activity
                    finish();
                    startActivity(intent);
                    return;
                }
                else
                {
                    // lets the user know that they didn't enter a name
                    Toast.makeText(MemberScreen.this, "Please enter a name", Toast.LENGTH_SHORT).show();
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
