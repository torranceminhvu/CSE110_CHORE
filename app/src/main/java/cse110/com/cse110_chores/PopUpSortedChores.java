package cse110.com.cse110_chores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Bailey on 11/20/15.
 */
public class PopUpSortedChores extends Activity {

    ArrayList<String> sortedChoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_window);

        // creates a display metric object
        DisplayMetrics dm = new DisplayMetrics();

        // gets the default display
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // gets the display metric width and height
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // sets the dimensions of the popup layout
        getWindow().setLayout( (int)(width * 0.6), (int)(height * 0.6));


        // connects the listview object
        ListView popUpSortedChoresListView = (ListView) findViewById(R.id.popUpSortedChoresListView);

        // gets the data from the previous intent
        Intent get = getIntent();
        sortedChoresList = get.getStringArrayListExtra("sortedChoresList");

        // sets the adapter to display the sortedChoresList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.pop_up_window_list_row, sortedChoresList);
        popUpSortedChoresListView.setAdapter(adapter);
    }
}
