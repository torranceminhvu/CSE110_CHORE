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

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout( (int)(width * 0.6), (int)(height * 0.6));


        ListView popUpSortedChoresListView = (ListView) findViewById(R.id.popUpSortedChoresListView);

        Intent get = getIntent();
        sortedChoresList = get.getStringArrayListExtra("sortedChoresList");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.pop_up_window_list_row, sortedChoresList);
        popUpSortedChoresListView.setAdapter(adapter);
    }
}
