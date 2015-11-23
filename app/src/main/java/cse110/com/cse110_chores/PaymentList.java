package cse110.com.cse110_chores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


public class PaymentList extends AppCompatActivity {

    private ListView paymentList;
    ArrayList<String> stringAL = new ArrayList<String>();
    ArrayList<Payments> paymentAL = new ArrayList<Payments>();
    DatabaseHandler db;
    Payments current;
    String display;

    String ownee;
    String owner;
    String amount;
    String description;

    PaymentListAdapter paymentListAdapter = null;

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

                finish();
                startActivity(intent);
                return;
            }
        });

        paymentAL = db.getAllPayments(groupid);
        for (int i = 0; i < paymentAL.size(); i++){
            current = paymentAL.get(i);
            ownee = current.getOwnee() + " owes ";
            owner = current.getOwner() + " $ ";
            amount = current.getAmount();
            display =  ownee + owner + amount;
            description = current.getDescription();
            stringAL.add(display);
        }

        /* private PaymentListAdapter(Context context, int resource, List<String> stringAL,
                                   List<Payments> paymentAL)
        */
        paymentListAdapter = new PaymentListAdapter(PaymentList.this, R.layout.payment_list_row,
                stringAL, paymentAL, db );
        paymentList.setAdapter(paymentListAdapter);
    }


/*
    private class PayAdapter extends ArrayAdapter<String> {
        private int layout;
        private PayAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder mainViewHolder = null;
            final int positionTwo = position + 1;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                final MyViewHolder myViewHolder = new MyViewHolder();
                myViewHolder.lineOne = (TextView) convertView.findViewById(R.id.oweReceiveAmount);
                myViewHolder.lineTwo = (TextView) convertView.findViewById(R.id.description);
                myViewHolder.paid = (Button) convertView.findViewById(R.id.paid_button);
                myViewHolder.paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deletePayment(paymentAL.get(position));
                        paymentAL.remove(position);
                        stringAL.remove(position);
                        theadapter.notifyDataSetChanged();
                    }
                });
                convertView.setTag(myViewHolder);
                myViewHolder.lineOne.setText(getItem(position));
                myViewHolder.lineTwo.setText(paymentAL.get(position).getDescription());
            }
            else {
                mainViewHolder = (MyViewHolder) convertView.getTag();
                mainViewHolder.lineOne.setText(getItem(position));
                mainViewHolder.lineTwo.setText(paymentAL.get(position).getDescription());
            }
            return convertView;
        }

    }
    public class MyViewHolder {
        TextView lineOne;
        TextView lineTwo;
        Button paid;
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

