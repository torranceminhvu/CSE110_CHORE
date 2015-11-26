package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by Bailey
 */
public class MemberList extends AppCompatActivity {

    private ListView memberList; // list view object to show the members
    ArrayList<String> stringAL = new ArrayList<String>(); // stores the member with a number
    ArrayList<Names> memberAL = new ArrayList<Names>(); // to hold the names obtained from the database
    String memberName; // string to hold the names obtained from the data base
    DatabaseHandler db; // to access the data base
    Names current; // holds current name object
    String display; // stores in string to add into stringAL
    Assigner assigner; // assigner object for assign chores later

    // custom adapter to show the list in a certain way
    MemberListAdapter memberListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        // gets all group information
        final int groupid = get.getIntExtra("GROUPID", 0);
        assigner = new Assigner(db, groupid);

        // links the add member button
        Button memberAddButton = (Button) findViewById(R.id.memberAddButton);

        // on click, will start a new activity to the member add page
        memberAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemberScreen.class);
                intent.putExtra("GROUPID", groupid);
                assigner.unassign();

                // finishes this activity and starts the next activity to add members
                finish();
                startActivity(intent);
                return;
            }
        });


        // links the listview
        memberList = (ListView) findViewById(R.id.memberlistview);

        // gets all the members and stores them into a stringAL to use for displaying
        memberAL = db.getAllNames(groupid);
        for (int i = 0; i < memberAL.size(); i++){
            current = memberAL.get(i);
            memberName = current.getName();
            display = String.valueOf(i+1) + ".  " + memberName;
            stringAL.add(display);
        }

        /*
            reminder on what parameters are needed
            MemberListAdapter(Context context, int resource, List<String> stringAL,
                            List<Names> memberAL,DatabaseHandler db)
         */
        memberListAdapter = new MemberListAdapter(MemberList.this, R.layout.member_list_row,
                stringAL, memberAL, db, groupid);
        memberList.setAdapter(memberListAdapter);
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

