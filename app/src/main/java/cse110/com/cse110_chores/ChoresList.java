package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


public class ChoresList extends AppCompatActivity {

    DatabaseHandler db;
    private ListView choreList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    Chores current;
    String name;
    String frequency;
    String display;

    myAdapter theadapter = null;

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
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stringAL);
        choreList.setAdapter(arrayAdapter);
*/
        theadapter = new myAdapter(ChoresList.this, R.layout.list_row, stringAL);
        choreList.setAdapter(theadapter);
    }

    /*
    public class myAdapter extends ArrayAdapter<String> {

        ArrayList<String> arr;
        private TextView text;
        public myAdapter(Context context, int textViewResourceId, ArrayList<String> objects ) {
            super(context, textViewResourceId, objects );
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
                    db.deleteChore(choreAL.get(position));
                    arr.remove(position);
                    theadapter.notifyDataSetChanged();
                    Toast.makeText(ChoresList.this, "Item deleted", Toast.LENGTH_SHORT).show();
                }
            });

            textnumber.setText( display );
            return view;
        }
    }*/
    private class myAdapter extends ArrayAdapter<String> {
        private int layout;
        private myAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder();
                myViewHolder.choreTitle = (TextView) convertView.findViewById(R.id.text);
                myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
                myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteChore(choreAL.get(position));
                        choreAL.remove(position);
                        stringAL.remove(position);
                        theadapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(myViewHolder);
                myViewHolder.choreTitle.setText(getItem(position));
            }
            else {
                mainViewholder = (MyViewHolder) convertView.getTag();
                mainViewholder.choreTitle.setText(getItem(position));
            }
            return convertView;
        }

    }
    public class MyViewHolder {
        TextView choreTitle;
        Button delete;
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
