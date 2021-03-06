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
    Context currActivity;

    private int layout;
    PaymentListAdapter(Context context, int resource, List<String> stringAL,
                       List<Payments> paymentAL, DatabaseHandler db) {
        super(context, resource, stringAL);
        layout = resource;
        this.stringAL = stringAL;
        this.paymentAL = paymentAL;
        this.db = db;
        this.paymentListAdapter = this;
        this.currActivity = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mainViewHolder;
        String lineOne;
        String lineTwo;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final MyViewHolder myViewHolder = new MyViewHolder();
            // links the textview object to show the line on text
            myViewHolder.lineOne = (TextView) convertView.findViewById(R.id.oweReceiveAmount);
            // links the textview object to show the line two text
            myViewHolder.lineTwo = (TextView) convertView.findViewById(R.id.description);
            // links the delete button to delete the object
            myViewHolder.paid = (Button) convertView.findViewById(R.id.paid_button);
            myViewHolder.paid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Asks user to confirm delete
                    AlertDialog.Builder builder = new AlertDialog.Builder(currActivity);
                    builder.setTitle("Confirm payment");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Removes the payment
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
                    builder.create().show();
                }
            });

            convertView.setTag(myViewHolder);
            // sets the texts for the textviews
            lineOne = getItem(position) + " owes "
                    + paymentAL.get(position).getOwner() + "    Amount: "
                    + "$" + paymentAL.get(position).getAmount();
            myViewHolder.lineOne.setText(lineOne);
            lineTwo = "For: " + paymentAL.get(position).getDescription();
            myViewHolder.lineTwo.setText(lineTwo);
            } else {

            mainViewHolder = (MyViewHolder) convertView.getTag();
            // sets the texts for the textviews
            lineOne = getItem(position) + " owes "
                    + paymentAL.get(position).getOwner() + "    Amount: "
                    + "$" + paymentAL.get(position).getAmount();
            mainViewHolder.lineOne.setText(lineOne);
            lineTwo = "For: " + paymentAL.get(position).getDescription();
            mainViewHolder.lineTwo.setText(lineTwo);
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