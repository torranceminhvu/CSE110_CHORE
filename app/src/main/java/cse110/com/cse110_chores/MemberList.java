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


public class MemberList extends AppCompatActivity {

    private ListView memberList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Names> memberAL = new ArrayList<Names>();
    String memberName;
    DatabaseHandler db;
    Names current;
    String display;
    Assigner assigner;

    MemberListAdapter memberListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);
        assigner = new Assigner(db, groupid);

        Button memberAddButton = (Button) findViewById(R.id.memberAddButton);

        memberAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemberScreen.class);
                intent.putExtra("GROUPID", groupid);
                assigner.unassign();

                finish();
                startActivity(intent);
                return;
            }
        });


        memberList = (ListView) findViewById(R.id.memberlistview);

        memberAL = db.getAllNames(groupid);
        for (int i = 0; i < memberAL.size(); i++){
            current = memberAL.get(i);
            memberName = current.getName();
            display = String.valueOf(i+1) + ".  " + memberName;
            stringAL.add(display);
        }

        /*
            MemberListAdapter(Context context, int resource, List<String> stringAL,
                            List<Names> memberAL,DatabaseHandler db)
         */
        memberListAdapter = new MemberListAdapter(MemberList.this, R.layout.member_list_row,
                stringAL, memberAL, db, groupid);
        memberList.setAdapter(memberListAdapter);
    }

    /*
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
                final MyViewHolder myViewHolder = new MyViewHolder();
                myViewHolder.memberTitle = (TextView) convertView.findViewById(R.id.text);
                myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
                myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteName(memberAL.get(position));
                        memberAL.remove(position);
                        //stringAL.remove(position);
                        stringAL.clear();
                        Intent get = getIntent();
                        final int groupid = get.getIntExtra("GROUPID", 0);
                        memberAL = db.getAllNames(groupid);
                        for (int i = 0; i < memberAL.size(); i++) {
                            current = memberAL.get(i);
                            memberName = current.getName();
                            display = String.valueOf(i + 1) + ".  " + memberName;
                            stringAL.add(display);
                        }

                        theadapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(myViewHolder);
                myViewHolder.memberTitle.setText(getItem(position));
            }
            else {
                mainViewholder = (MyViewHolder) convertView.getTag();
                mainViewholder.memberTitle.setText(getItem(position));
            }
            return convertView;
        }

    }
    public class MyViewHolder {
        TextView memberTitle;
        Button delete;
    }
    */

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

