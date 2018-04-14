package akai.com.todolistapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Wojtek
 *
 * This class is responsible for RecyclerView Activity
 */

public class RecyclerViewBackground extends AppCompatActivity{

    static final int ADD_TASK_REQUEST = 1;
    static final int EDIT_TASK_REQUEST = 2;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Task> tasks;
    private int selectedPosition = -1;

    private ActionMode.Callback itemActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_delete_edit, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    ConfirmFragment confirmFragment = new ConfirmFragment();
                    confirmFragment.setMessage(getString(R.string.delete_task_message));
                    confirmFragment.setListener(new ConfirmFragment.DialogListener() {
                        @Override
                        public void onPositiveClick() {
                            DeleteTask deleteTask = new DeleteTask(tasks.get(selectedPosition));
                            deleteTask.execute();
                            tasks.remove(selectedPosition);
                            mode.finish();
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                    confirmFragment.show(getSupportFragmentManager(), "dialog");
                    return true;
                case R.id.edit:
                    Intent intent = new Intent(RecyclerViewBackground.this, AddTaskActivity.class);
                    intent.putExtra(AddTaskActivity.TASK, tasks.get(selectedPosition));
                    startActivityForResult(intent, EDIT_TASK_REQUEST);
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.setSelectedPosition(-1);
            mAdapter.notifyDataSetChanged();
            selectedPosition = -1;
        }
    };

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
            tasks = dbhelper.getAll();
            mAdapter = new MyAdapter(this, tasks);
        } catch (Exception e){
            //TODO
            //I know, not very wise
            Log.d("ADAPTER", "Exception catched" + e.getMessage() + " " + e.getCause());
            finishActivity(0);
        }
        mAdapter.setOnLongClickListener(new MyAdapter.OnLongClickListener() {
            @Override
            public void onClick(int position) {
                selectedPosition = position;
                startSupportActionMode(itemActionModeCallback);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
                return true;
            case R.id.delete:
                startSupportActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mAdapter.setMultiSelect(true);
                        menu.add(getString(R.string.delete));
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        List<Task> toDelete = mAdapter.getSelectedItems();
                        if(toDelete.size() > 0) {
                            DeleteTask deleteTask = new DeleteTask(toDelete);
                            deleteTask.execute();
                            tasks.removeAll(toDelete);
                        }
                        mode.finish();
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        mAdapter.setMultiSelect(false);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ADD_TASK_REQUEST) {
            if(resultCode==RESULT_OK) {
                Task addedTask = (Task) data.getSerializableExtra(AddTaskActivity.TASK);
                insertTaskToList(addedTask);
                mAdapter.notifyDataSetChanged();
            }
        }
        else if(requestCode==EDIT_TASK_REQUEST) {
            if(resultCode==RESULT_OK) {
                Task updatedTask = (Task) data.getSerializableExtra(AddTaskActivity.TASK);
                int i = 0;
                while(tasks.get(i).getId() != updatedTask.getId()) {
                    i++;
                }
                tasks.remove(i);
                insertTaskToList(updatedTask);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void insertTaskToList(Task task) {
        int i = 0;
        while(i < tasks.size() && compareTasks(task, tasks.get(i)) != -1) {
            i++;
        }
        tasks.add(i, task);
    }

    private int compareTasks(Task task1, Task task2) {
        if(!task1.getPriority().equals(task2.getPriority())) {
            if(task1.getPriority()) {
                return -1;
            }
            else {
                return 1;
            }
        }
        if(task1.getDate().get(Calendar.YEAR) > task2.getDate().get(Calendar.YEAR)) {
            return 1;
        }
        if(task1.getDate().get(Calendar.YEAR) < task2.getDate().get(Calendar.YEAR)) {
            return -1;
        }
        if(task1.getDate().get(Calendar.MONTH) > task2.getDate().get(Calendar.MONTH)) {
            return 1;
        }
        if(task1.getDate().get(Calendar.MONTH) < task2.getDate().get(Calendar.MONTH)) {
            return -1;
        }
        if(task1.getDate().get(Calendar.DAY_OF_MONTH) > task2.getDate().get(Calendar.DAY_OF_MONTH)) {
            return 1;
        }
        if(task1.getDate().get(Calendar.DAY_OF_MONTH) < task2.getDate().get(Calendar.DAY_OF_MONTH)) {
            return -1;
        }
        return 0;
    }

    private class DeleteTask extends AsyncTask<Void, Void, Boolean> {

        private List<Task> taskList;
        private Task task;

        DeleteTask(List<Task> taskList) {
            this.taskList = taskList;
        }

        DeleteTask(Task task) {
            this.task = task;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            DBHelper dbHelper = new DBHelper(RecyclerViewBackground.this);
            if(task == null) {
                dbHelper.delete(taskList);
            }
            else {
                dbHelper.delete(task);
            }
            return true;
        }
    }
}

