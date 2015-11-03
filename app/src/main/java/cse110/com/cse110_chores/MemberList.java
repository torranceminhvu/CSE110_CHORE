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
        import java.util.List;


public class MemberList extends AppCompatActivity {

    private ListView memberList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Names> memberAL = new ArrayList<Names>();
    String memberName;
    DatabaseHandler db;
    Names current;
    String display;

    myAdapter theadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        db = new DatabaseHandler(this);
        Intent get = getIntent();
        final int groupid = get.getIntExtra("GROUPID", 0);

        Button memberAddButton = (Button) findViewById(R.id.memberAddButton);

        memberAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemberScreen.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);
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
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stringAL);*/
        theadapter = new myAdapter(MemberList.this, R.layout.member_list_row, stringAL);
        memberList.setAdapter(theadapter);

    }

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
                myViewHolder.memberTitle = (TextView) convertView.findViewById(R.id.text);
                myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
                myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteName(memberAL.get(position));
                        memberAL.remove(position);
                        stringAL.remove(position);
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
    /*
        @Override
        public View getView( final int position, View convertView, ViewGroup parent ) {

            View view;
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.member_list_row, parent, false);
            TextView textnumber = (TextView) view.findViewById(R.id.text);
            Button delete_button = (Button) view.findViewById(R.id.delete_button);

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arr.remove(position);
                    memberAdapater.notifyDataSetChanged();
                    Toast.makeText(MemberList.this, "Item deleted", Toast.LENGTH_SHORT).show();
                }
            });

            textnumber.setText( memberName );
            return view;
        }
    } */


}

