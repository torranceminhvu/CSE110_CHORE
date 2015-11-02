package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private ListView choreList;
    MyThumbnailAdapter choreAdapater = null;
    ArrayList<String> choreAL = new ArrayList<String>();
    String choreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        Button choreAddButton = (Button) findViewById(R.id.choreAddButton);

        choreAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChoresScreen.class);
                startActivityForResult(intent, 0);
            }
        });

        Intent addChoreIntent = getIntent();

        choreName = addChoreIntent.getStringExtra("chore");

        choreList = (ListView) findViewById(R.id.chorelistview);

        //for ( i = chore_count; i < add_count; i++ ) {
        choreAL.add(choreName);
        //}

        choreAdapater = new MyThumbnailAdapter(ChoresList.this, R.layout.list_row, choreAL);
        choreList.setAdapter(choreAdapater);
    }

    public class MyThumbnailAdapter extends ArrayAdapter<String> {

        ArrayList<String> arr;

        public MyThumbnailAdapter( Context context, int textViewResourceId, ArrayList<String> objects) {
            super( context, textViewResourceId, objects);
            this.arr = objects;
        }

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

            textnumber.setText( choreName );
            return view;
        }
    }


}
