package akai.com.todolistapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Wojtek
 *
 * This class is responsible for RecyclerView Activity
 */

public class RecyclerViewBackground extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        DBHelper dbhelper = new DBHelper(RecyclerViewBackground.this);
        try {
            mAdapter = new MyAdapter(dbhelper.getAll());
        } catch (Exception e){
            //TODO
            //I know, not very wise
            Log.d("ADAPTER", "Exception catched" + e.getMessage() + " " + e.getCause());
            finishActivity(0);
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}

