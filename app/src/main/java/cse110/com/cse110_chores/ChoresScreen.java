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

public class ChoresScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHandler db;
    Spinner spinner;
    String frequency = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_screen);

        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        db = new DatabaseHandler(this);
        spinner= (Spinner) findViewById(R.id.choreSpinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.frequency, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_SHORT).show();
                frequency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button addChore = (Button) findViewById(R.id.addChore);

        final EditText choreText = (EditText) findViewById(R.id.chore);

        addChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                Intent intent = new Intent(v.getContext(), ChoresList.class);
                String chore = choreText.getText().toString();
                db.addChore(new Chores(chore, frequency, groupid));

                intent.putExtra("GROUPID", groupid);
                startActivity(intent);
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
                startActivity(new Intent(this, MainScreen.class));
                break;
            case R.id.menu_logout:
                startActivity(new Intent(this, CreateSearch_Group.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
