package cse110.com.cse110_chores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {
    //todo: display group name by getting intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);

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
    }



}
