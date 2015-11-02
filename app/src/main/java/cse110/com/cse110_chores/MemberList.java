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
    MyThumbnailAdapter memberAdapater = null;
    ArrayList<String> memberAL = new ArrayList<String>();
    int member_count = 0;
    int add_member_count = 0;
    int i = 0;
    String memberName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        Button memberAddButton = (Button) findViewById(R.id.memberAddButton);

        memberAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemberScreen.class);
                startActivityForResult(intent, 0);
            }
        });

        Intent addChoreIntent = getIntent();

        memberName = addChoreIntent.getStringExtra("member");

        add_member_count = addChoreIntent.getIntExtra("add_member_count", add_member_count);

        memberList = (ListView) findViewById(R.id.memberlistview);

        //for ( i = member_count; i < add_member_count; i++ ) {
        memberAL.add(memberName);
        //}
        i++;

        memberAdapater = new MyThumbnailAdapter(MemberList.this, R.layout.member_list_row, memberAL);
        memberList.setAdapter(memberAdapater);
    }

    public class MyThumbnailAdapter extends ArrayAdapter<String> {

        ArrayList<String> arr;
        private TextView text;

        public MyThumbnailAdapter( Context context, int textViewResourceId, ArrayList<String> objects) {
            super( context, textViewResourceId, objects);
            this.arr = objects;
        }

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
    }


}

