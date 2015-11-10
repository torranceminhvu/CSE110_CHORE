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
    String personOwe;
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
        paymentList = (ListView) findViewById(R.id.paymentListView);

        Button paymentAddButton = (Button) findViewById(R.id.paymentAddButton);

        paymentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PaymentScreen.class);
                intent.putExtra("GROUPID", groupid);
                startActivityForResult(intent, 0);
            }
        });

        for (int i = 0; i < paymentAL.size(); i++){
            current = paymentAL.get(i);
            personOwe = current.getName();
            display =  personOwe;
            stringAL.add(display);
        }

        theadapter = new myAdapter(PaymentList.this, R.layout.list_row, stringAL);
        paymentList.setAdapter(theadapter);
    }

    private class myAdapter extends ArrayAdapter<String> {
        private int layout;
        private myAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
       /* @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder();
                myViewHolder.paymentTitle = (TextView) convertView.findViewById(R.id.text);
                myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
                myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteChore(paymentAL.get(position));
                        paymentAL.remove(position);
                        stringAL.remove(position);
                        theadapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(myViewHolder);
                myViewHolder.paymentTitle.setText(getItem(position));
            }
            else {
                mainViewholder = (MyViewHolder) convertView.getTag();
                mainViewholder.paymentTitle.setText(getItem(position));
            }
            return convertView;
        } */

    }

    public class MyViewHolder {
        TextView paymentTitle;
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
                startActivity(new Intent(this, CreateSearch_Group.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

