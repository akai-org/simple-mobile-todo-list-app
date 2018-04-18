package akai.com.todolistapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class SettingsSection extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SettingsElement> mListElements;
    private String SectionName;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_section_layout);
        mRecyclerView = findViewById(R.id.settings_section_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SettingsSectionAdapter(mListElements);

        mRecyclerView.setAdapter(mAdapter);
    }

    public String getSectionName() {
        return SectionName;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public List<SettingsElement> getmListElements() {
        return mListElements;
    }

    public SettingsSection(String sectionName, List<SettingsElement> mList){
        this.SectionName = sectionName;
        this.mListElements = mList;
    }
}
