package cse110.com.cse110_chores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bailey on 11/22/15.
 */
public class PaymentListAdapter extends ArrayAdapter<String> {

    List<String> stringAL;
    List<Payments> paymentAL;
    DatabaseHandler db;
    PaymentListAdapter paymentListAdapter;

    private int layout;
        PaymentListAdapter(Context context, int resource, List<String> stringAL,
                           List<Payments> paymentAL, DatabaseHandler db) {
            super(context, resource, stringAL);
            layout = resource;
            this.stringAL = stringAL;
            this.paymentAL = paymentAL;
            this.db = db;
            this.paymentListAdapter = this;
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
                        paymentListAdapter.notifyDataSetChanged();
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

    public class MyViewHolder {
        TextView lineOne;
        TextView lineTwo;
        Button paid;
    }

}