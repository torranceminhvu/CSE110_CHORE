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
public class ChoreListAdapter extends ArrayAdapter<String> {
    private int layout;
    List<String> stringAL;
    List<Chores> choreAL;
    List<Names> namesAL;
    List<ChoreName> choreNameCheck;
    DatabaseHandler db;
    ChoreListAdapter choreAdapter;

    ChoreListAdapter(Context context, int resource, List<String> stringAL,
                     List<Chores> choreAL, List<Names> namesAL,
                     List<ChoreName> choreNameCheck, DatabaseHandler db) {
        super(context, resource, stringAL);
        layout = resource;
        this.stringAL = stringAL;
        this.choreAL = choreAL;
        this.namesAL = namesAL;
        this.choreNameCheck = choreNameCheck;
        this.db = db;
        this.choreAdapter = this;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mainViewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder();
            myViewHolder.frequencyTextView = (TextView) convertView.findViewById(R.id.frequencyTextView);
            myViewHolder.choreTextView = (TextView) convertView.findViewById(R.id.choreTextView);
            myViewHolder.personTextView = (TextView) convertView.findViewById(R.id.personTextView);
            myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteChore(choreAL.get(position));
                    choreAL.remove(position);
                    stringAL.remove(position);
                    namesAL.remove(position);
                    db.deleteChoreName(choreNameCheck.get(position));
                    choreAdapter.notifyDataSetChanged();
                }
            });
            convertView.setTag(myViewHolder);
            myViewHolder.frequencyTextView.setText("Frequency: " + choreAL.get(position).getFrequency());
            myViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
            myViewHolder.personTextView.setText("");
            if (namesAL.size() != 0) {
                myViewHolder.personTextView.setText("Name: " + namesAL.get(position).getName());
            }
        } else {
            mainViewHolder = (MyViewHolder) convertView.getTag();
            mainViewHolder.frequencyTextView.setText("Frequency: " + choreAL.get(position).getFrequency());
            mainViewHolder.choreTextView.setText("Chore: " + choreAL.get(position).getName());
            mainViewHolder.personTextView.setText("");
            if (namesAL.size() != 0) {
                mainViewHolder.personTextView.setText("Name: " + namesAL.get(position).getName());
            }
        }
        return convertView;
    }

    public class MyViewHolder {
        TextView frequencyTextView;
        TextView choreTextView;
        TextView personTextView;
        Button delete;
    }
}
