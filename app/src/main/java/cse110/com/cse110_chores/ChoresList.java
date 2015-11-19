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

import java.util.ArrayList;
import java.util.List;


public class ChoresList extends AppCompatActivity {

    DatabaseHandler db;
    private ListView choreList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Chores> choreAL = new ArrayList<Chores>();
    ArrayList<Names> namesAL = new ArrayList<Names>();
    Assigner assigner;
    Chores current;
    String chore;
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

                finish();
                startActivity(intent);
                return;
            }
        });

        assigner = new Assigner(db, groupid);
        namesAL = assigner.getAllIndex();

        if (namesAL.size() == 0) {
            choreAL = db.getAllChores(groupid);
            for (int i = 0; i < choreAL.size(); i++) {
                current = choreAL.get(i);
                chore = current.getName();
                frequency = current.getFrequency();
                display = frequency + ":  " + chore;
                stringAL.add(display);
            }
        } else {
            choreAL = db.getAllChores(groupid);
            for (int i = 0; i < choreAL.size(); i++) {
                current = choreAL.get(i);
                chore = current.getName();
                frequency = current.getFrequency();
                display = frequency + ":  " + chore + "             " + namesAL.get(i).getName();
                //display = frequency + ":  " + chore;
                stringAL.add(display);
            }

        }

        // Added button to assign chore
        Button assignChore = (Button) findViewById(R.id.assignChore);

        assignChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choreAL = db.getAllChores(groupid);
                assigner.assign();
                namesAL = assigner.getAllIndex();
                //Log.e("NAMESAL", String.valueOf(namesAL.size()));
                if (namesAL.size() != 0){
                    stringAL.clear();
                    for (int i = 0; i < choreAL.size(); i++) {
                        current = choreAL.get(i);
                        chore = current.getName();
                        frequency = current.getFrequency();
                        display = frequency + ":  " + chore + "             " + namesAL.get(i).getName();
                        stringAL.add(display);
                        theadapter.notifyDataSetChanged();
                    }
                }
            }
        });

        theadapter = new myAdapter(ChoresList.this, R.layout.chores_list_row, stringAL);

        choreList.setAdapter(theadapter);
    }


    // custom adapter to display in listview
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
