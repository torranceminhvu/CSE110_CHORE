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
 * Created by Justice on 11/26/2015.
 */
public class EventListAdapter extends ArrayAdapter<String> {

    List<String> stringAL;
    List<Events> eventAL;
    DatabaseHandler db;
    EventListAdapter eventListAdapter;
    Context currActivity;

    private int layout;
    EventListAdapter(Context context, int resource, List<String> stringAL,
                       List<Events> eventAL, DatabaseHandler db) {
        super(context, resource, stringAL);
        layout = resource;
        this.stringAL = stringAL;
        this.eventAL = eventAL;
        this.db = db;
        this.eventListAdapter = this;
        this.currActivity = context;
    }
    /*
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mainViewHolder = null;
        final int positionTwo = position + 1;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final MyViewHolder myViewHolder = new MyViewHolder();
            // links the textview object to show the line on text
            myViewHolder.lineOne = (TextView) convertView.findViewById(R.id.eventAndDate);
            // links the textview object to show the line two text
            myViewHolder.lineTwo = (TextView) convertView.findViewById(R.id.startAndEndTime);

            myViewHolder.lineThree = (TextView) convertView.findViewById(R.id.eventDescription);
            // links the delete button to delete the object
            myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Asks user to confirm delete
                    AlertDialog.Builder builder = new AlertDialog.Builder(currActivity);
                    builder.setTitle("Confirm delete");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Removes the event
                            db.deleteEvent(eventAL.get(position));
                            eventAL.remove(position);
                            stringAL.remove(position);
                            eventListAdapter.notifyDataSetChanged();
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
            myViewHolder.lineOne.setText(getItem(position));
            myViewHolder.lineTwo.setText(eventAL.get(position).getDescription());
            myViewHolder.lineThree.setText(eventAL.get(position).getDescription());
        } else {

            mainViewHolder = (MyViewHolder) convertView.getTag();
            // sets the texts for the textviews
            mainViewHolder.lineOne.setText(getItem(position));
            mainViewHolder.lineTwo.setText(eventAL.get(position).getDescription());
            mainViewHolder.lineThree.setText(eventAL.get(position).getDescription());
        }
        return convertView;
    }

    // class used for setting up the custom display
    public class MyViewHolder {
        TextView lineOne;
        TextView lineTwo;
        TextView lineThree;
        Button delete;
    }
    */
}
