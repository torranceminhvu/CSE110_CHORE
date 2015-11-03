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


public class MemberList extends AppCompatActivity {

    private ListView memberList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Names> memberAL = new ArrayList<Names>();
    String memberName;
    DatabaseHandler db;
    Names current;
    String display;

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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stringAL);
        memberList.setAdapter(arrayAdapter);

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

