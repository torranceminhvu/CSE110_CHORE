package cse110.com.cse110_chores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

                // links the textview object to show the line one text
                myViewHolder.lineOne = (TextView) convertView.findViewById(R.id.oweReceiveAmount);
                // links the textview object to show the line two text
                myViewHolder.lineTwo = (TextView) convertView.findViewById(R.id.description);
                // links the delete button to delete the object
                myViewHolder.paid = (Button) convertView.findViewById(R.id.paid_button);
                myViewHolder.paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Asks user to confirm delete
                        /*
                        AlertDialog.Builder builder = new AlertDialog.Builder();
                        builder.setTitle("Confirm payment");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletePayment(paymentAL.get(position));
                                paymentAL.remove(position);
                                stringAL.remove(position);
                                paymentListAdapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                        */
                        db.deletePayment(paymentAL.get(position));
                        paymentAL.remove(position);
                        stringAL.remove(position);
                        paymentListAdapter.notifyDataSetChanged();
                    }
                });

                convertView.setTag(myViewHolder);
                // sets the texts for the textviews
                myViewHolder.lineOne.setText(getItem(position));
                myViewHolder.lineTwo.setText(paymentAL.get(position).getDescription());
            } else {

                mainViewHolder = (MyViewHolder) convertView.getTag();
                // sets the texts for the textviews
                mainViewHolder.lineOne.setText(getItem(position));
                mainViewHolder.lineTwo.setText(paymentAL.get(position).getDescription());
            }
            return convertView;
        }

    // class used for setting up the custom display
    public class MyViewHolder {
        TextView lineOne;
        TextView lineTwo;
        Button paid;
    }

}