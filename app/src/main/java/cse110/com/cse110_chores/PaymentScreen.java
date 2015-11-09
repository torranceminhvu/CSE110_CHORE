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

public class PaymentScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);

        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        db = new DatabaseHandler(this);

        Button addPayment = (Button) findViewById(R.id.addPayment);

        final EditText personOwes = (EditText) findViewById(R.id.personOwes);
        final EditText personReceiving = (EditText) findViewById(R.id.personReceiving);
        final EditText amount = (EditText) findViewById(R.id.amount);
        final EditText description = (EditText) findViewById(R.id.description);

        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PaymentList.class);
                String nameOwe = personOwes.getText().toString();
                String nameReceive = personReceiving.getText().toString();
                String amountOwed = amount.getText().toString();
                String des = description.getText().toString();

                db.addName(new Names(nameOwe, groupid));
                db.addName(new Names(nameReceive, groupid));
                db.addName(new Names(amountOwed, groupid));
                db.addName(new Names(des, groupid));

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
