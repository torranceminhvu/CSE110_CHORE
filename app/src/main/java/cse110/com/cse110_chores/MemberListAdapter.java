package cse110.com.cse110_chores;

import android.content.Context;
import android.content.Intent;
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
public class MemberListAdapter extends ArrayAdapter<String> {

    List<String> stringAL;
    List<Names> memberAL;
    DatabaseHandler db;
    MemberListAdapter memberListAdapter;
    Names current;
    String memberName;
    String display;
    Assigner assigner;
    int groupid;

    private int layout;

    MemberListAdapter(Context context, int resource, List<String> stringAL, List<Names> memberAL,
                      DatabaseHandler db, int groupid) {
        super(context, resource, stringAL);
        layout = resource;
        this.stringAL = stringAL;
        this.memberAL = memberAL;
        this.db = db;
        this.memberListAdapter = this;
        this.groupid = groupid;
        assigner = new Assigner(db, groupid);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mainViewholder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final MyViewHolder myViewHolder = new MyViewHolder();
            // links the textview object to show the member
            myViewHolder.memberTitle = (TextView) convertView.findViewById(R.id.text);
            // links the delete button to delete the object
            myViewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteName(memberAL.get(position));
                    memberAL.remove(position);
                    stringAL.clear();
                    for (int i = 0; i < memberAL.size(); i++) {
                        current = memberAL.get(i);
                        memberName = current.getName();
                        display = String.valueOf(i + 1) + ".  " + memberName;
                        stringAL.add(display);
                    }
                    assigner.unassign();
                    memberListAdapter.notifyDataSetChanged();
                }
            });

            convertView.setTag(myViewHolder);
            // sets the texts for the textviews
            myViewHolder.memberTitle.setText(getItem(position));
        } else {

            mainViewholder = (MyViewHolder) convertView.getTag();
            // sets the texts for the textviews
            mainViewholder.memberTitle.setText(getItem(position));
        }
        return convertView;
    }

    // class used for setting up the custom display
    class MyViewHolder {
        TextView memberTitle;
        Button delete;
    }
}

