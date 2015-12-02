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
public class ChoreListAdapter extends ArrayAdapter<String> {

    private int layout;
    List<String> stringAL;
    List<Chores> choreAL;
    List<Names> namesAL;
    List<ChoreName> choreNameCheck;
    DatabaseHandler db;
    ChoreListAdapter choreAdapter;
    Assigner assigner;
    Context currActivity;
    int groupid;

    ChoreListAdapter(Context context, int resource, List<String> stringAL,
                     List<Chores> choreAL, List<Names> namesAL,
                     List<ChoreName> choreNameCheck, DatabaseHandler db, int groupid) {
        super(context, resource, stringAL);
        layout = resource;
        this.stringAL = stringAL;
        this.choreAL = choreAL;
        this.namesAL = namesAL;
        this.choreNameCheck = choreNameCheck;
        this.db = db;
        this.choreAdapter = this;
        this.groupid = groupid;
        this.currActivity = context;
        assigner = new Assigner(db, groupid);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mainViewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder();

            // links the textview object to show the frequency
            myViewHolder.frequencyTextView = (TextView)
                    convertView.findViewById(R.id.frequencyTextView);
            // links the textview object to show the chore name
            myViewHolder.choreTextView = (TextView) convertView.findViewById(R.id.choreTextView);
            // links the textview object to show the person who does the chore
            myViewHolder.personTextView = (TextView) convertView.findViewById(R.id.personTextView);
            // links the delete button to delete the object
            myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // confirmation of delete
                    AlertDialog.Builder builder = new AlertDialog.Builder(currActivity);
                    builder.setTitle("Confirm delete");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            db.deleteChore(choreAL.get(position));
                            choreAL.remove(position);
                            stringAL.remove(position);
                            //namesAL.remove(position);
                            assigner.unassign();
                            choreAdapter.notifyDataSetChanged();
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

            // sets the texts for the textviews
            convertView.setTag(myViewHolder);
            myViewHolder.frequencyTextView.setText("Frequency: " +
                    choreAL.get(position).getFrequency());
            myViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
            myViewHolder.personTextView.setText("");
            if (choreNameCheck.size() != 0) {
                myViewHolder.personTextView.setText("Name: " + namesAL.get(position).getName()
                        + "         Day: " + (choreNameCheck.get(position).getCounter() + 1));
            }
        } else {

            // sets the texts for the textviews
            mainViewHolder = (MyViewHolder) convertView.getTag();
            mainViewHolder.frequencyTextView.setText("Frequency: " +
                    choreAL.get(position).getFrequency());
            mainViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
            mainViewHolder.personTextView.setText("");
            if (choreNameCheck.size() != 0) {
                mainViewHolder.personTextView.setText("Name: " + namesAL.get(position).getName()
                        + "         Day: " + (choreNameCheck.get(position).getCounter() + 1));
            }
        }
        return convertView;
    }

    // class used for setting up the custom display
    public class MyViewHolder {
        TextView frequencyTextView;
        TextView choreTextView;
        TextView personTextView;
        Button delete;
    }
}

