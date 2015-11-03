package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChoresList extends AppCompatActivity {

    DatabaseHandler db;
    private ListView choreList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    Chores current;
    String name;
    String frequency;
    String display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        choreList = (ListView) findViewById(R.id.chorelistview);

        Button choreAddButton = (Button) findViewById(R.id.choreAddButton);

        choreAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChoresScreen.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);
            }
        });

        choreAL = db.getAllChores(groupid);
        for (int i = 0; i < choreAL.size(); i++){
            current = choreAL.get(i);
            name = current.getName();
            frequency = current.getFrequency();
            display = frequency + ":  " + name;
            stringAL.add(display);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stringAL);
        choreList.setAdapter(arrayAdapter);
    }

    /*
        @Override
        public View getView( final int position, View convertView, ViewGroup parent ) {

            View view;
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.list_row, parent, false);
            TextView textnumber = (TextView) view.findViewById(R.id.text);
            Button delete_button = (Button) view.findViewById(R.id.delete_button);

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arr.remove(position);
                    choreAdapater.notifyDataSetChanged();
                    Toast.makeText(ChoresList.this, "Item deleted", Toast.LENGTH_SHORT).show();
                }
            });

            textnumber.setText( display );
            return view;
        }
    }
*/

}
