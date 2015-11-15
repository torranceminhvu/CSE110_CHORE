package cse110.com.cse110_chores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateSearch_Group extends AppCompatActivity {
    DatabaseHandler db;

    //should work now

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_search__group);
        final EditText groupText = (EditText) findViewById(R.id.username);
        final EditText passText = (EditText) findViewById(R.id.password);

        db = new DatabaseHandler(this);
        Button loginbutton = (Button) findViewById(R.id.login);
        Button createbutton = (Button) findViewById(R.id.create);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String group = groupText.getText().toString();
                String pass = passText.getText().toString();

                //display wrong username/password
                if (!db.getGroup(group).getFound() ||
                        !(db.getGroup(group).getPassword().equals(pass))){
                    Toast.makeText(CreateSearch_Group.this, "Wrong Username/Password",
                            Toast.LENGTH_SHORT).show();
                }
                //successful login
                else {
                    Intent intent = new Intent(v.getContext(), MainScreen.class);
                    int groupid = db.getGroup(group).getId();
                    intent.putExtra("GROUPID", groupid);
                    // send title to mainscreen
                    intent.putExtra("groupText", group );
                    startActivityForResult(intent, 0);
                }
            }
        });

       createbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String group = groupText.getText().toString();
                String pass = passText.getText().toString();

                //successful create group
                if (!db.getGroup(group).getFound()){
                    db.addGroup(new Groups(group, pass));
                    Intent intent = new Intent(v.getContext(), MainScreen.class);
                    int groupid = db.getGroup(group).getId();
                    intent.putExtra("GROUPID", groupid);
                    // send title to mainscreen
                    intent.putExtra("groupText", group );
                    startActivityForResult(intent, 0);
                }
                //duplicate group
                else{
                    Toast.makeText(CreateSearch_Group.this, "Username already in use",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
