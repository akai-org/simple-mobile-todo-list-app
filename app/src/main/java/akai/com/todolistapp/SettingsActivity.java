package akai.com.todolistapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SettingsSection> mListSections = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);
        mRecyclerView = findViewById(R.id.settings_main_recycler_view);

        //here we add new options
        List<SettingsElement> testElements = new ArrayList<>();
        testElements.add(new TrialOption());
        testElements.add(new TrialOption());
        SettingsSection testSection = new SettingsSection("test",testElements);
        mListSections.add(testSection);

        List<SettingsElement> testElements2 = new ArrayList<>();
        testElements2.add(new TrialOption());
        SettingsSection testSection2 = new SettingsSection("test2",testElements2);
        mListSections.add(testSection2);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        try {
            mAdapter = new SettingsActivityAdapter(mListSections);
        } catch (Exception e){
            Log.d("SETTINGS_SECTION", "Exception catched" + e.getMessage() + " " + e.getCause());
            finishActivity(0);
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}
