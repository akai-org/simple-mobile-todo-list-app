package akai.com.todolistapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    public static final String ID = "id"; //used to pass id of task in intent

    private EditText nameEditText;
    private DatePicker datePicker;
    private CheckBox priorityCheckBox;
    private Task taskToEdit;

    private static final int INSERT = 0;
    private static final int UPDATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        priorityCheckBox = (CheckBox) findViewById(R.id.priorityCheckBox);

        Intent intent = getIntent();
        int id = intent.getIntExtra(ID, -1);
        if(id!=-1) {
            GetTask getTask = new GetTask();
            getTask.execute(id);
        }

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskToEdit==null) {
                    addTask();
                }
                else {
                    editTask();
                }
            }
        });
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addTask() {
        String taskName = nameEditText.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        try {
            Task task = new Task(taskName, calendar, false);
            if(priorityCheckBox.isChecked()) {
                task.setPriority(true);
            }
            SaveTask saveTask = new SaveTask(INSERT);
            saveTask.execute(task);
            finish();
        } catch(Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editTask() {
        try {
            taskToEdit.setTitle(nameEditText.getText().toString());
            taskToEdit.setPriority(priorityCheckBox.isChecked());
            Calendar calendar = Calendar.getInstance();
            calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            taskToEdit.setDate(calendar);
            SaveTask saveTask = new SaveTask(UPDATE);
            saveTask.execute(taskToEdit);
            finish();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class GetTask extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... id) {
            DBHelper dbHelper = new DBHelper(AddTaskActivity.this);
            try {
                taskToEdit = dbHelper.get(id[0]);
            } catch(Exception ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                nameEditText.setText(taskToEdit.getTitle());
                priorityCheckBox.setChecked(taskToEdit.getPriority());
                Calendar calendar = taskToEdit.getDate();
                datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
    }

    private class SaveTask extends AsyncTask<Task, Void, Boolean> {
        private int action;

        SaveTask(int action) {
            this.action = action;
        }

        @Override
        protected Boolean doInBackground(Task... params){
            DBHelper dbHelper = new DBHelper(AddTaskActivity.this);
            switch (action) {
                case INSERT:
                    dbHelper.add(params[0]);
                    return true;
                case UPDATE:
                    try {
                        dbHelper.update(params[0]);
                        return true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
