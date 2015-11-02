package cse110.com.cse110_chores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;import java.lang.Override;import java.lang.String;import cse110.com.cse110_chores.R;

public class MemberScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static int add_member_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_screen);

        Button addMember = (Button) findViewById(R.id.addMember);

        final EditText memberText = (EditText) findViewById(R.id.member);

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                Intent intent = new Intent(v.getContext(), MemberList.class);
                String member = memberText.getText().toString();
                intent.putExtra("member", member);
                add_member_count++;
                intent.putExtra("add_member_count", add_member_count);


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
}
