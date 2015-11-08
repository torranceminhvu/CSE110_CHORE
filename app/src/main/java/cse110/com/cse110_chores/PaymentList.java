package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PaymentList extends AppCompatActivity {

    private ListView paymentList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Names> paymentAL = new ArrayList<Names>();
    String personOwe, personReceiving;
    DatabaseHandler db;
    Names current;
    String display;

    myAdapter theadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);

        Button paymentAddButton = (Button) findViewById(R.id.paymentAddButton);

        paymentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PaymentScreen.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);
            }
        });
    }

    private class myAdapter extends ArrayAdapter<String> {
        private int layout;

        private myAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
    }
}

