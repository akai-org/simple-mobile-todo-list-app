package akai.com.todolistapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //here we add elements
    //sections as String
    //Elements as new objects - instances of SettingsElement class
    //you need to overwrite constructor to change name displayed in recycler
    //start() method is executed in onClick so you need to overwrite it to execute
    //your settings function
    //Example below
    private ArrayList<Object> getItemsList(){
        ArrayList<Object> myList = new ArrayList<>();
        myList.add("Task settings");
        myList.add(new SettingsDatabaseSort(SettingsActivity.this,this));

        return myList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);
        mRecyclerView = findViewById(R.id.settings_main_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        try {
            mAdapter = new SettingsActivityAdapter(getItemsList());
        } catch (Exception e){
            Log.d("SETTINGS_SECTION", "Exception catched" + e.getMessage() + " " + e.getCause());
            finishActivity(0);
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}
