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
import java.util.GregorianCalendar;

public class AddTaskActivity extends AppCompatActivity {

    public static final String TASK = "task"; //used to pass tasks in intent

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
        taskToEdit = (Task) intent.getSerializableExtra(TASK);
        if(taskToEdit != null) {
            fillWithDataToEdit();
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

    private void fillWithDataToEdit() {
        nameEditText.setText(taskToEdit.getTitle());
        priorityCheckBox.setChecked(taskToEdit.getPriority());
        Calendar calendar = taskToEdit.getDate();
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void addTask() {
        String taskName = nameEditText.getText().toString();
        Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        try {
            Task task = new Task(taskName, calendar, false);
            if(priorityCheckBox.isChecked()) {
                task.setPriority(true);
            }
            SaveTask saveTask = new SaveTask(INSERT);
            saveTask.execute(task);
        } catch(Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editTask() {
        try {
            taskToEdit.setTitle(nameEditText.getText().toString());
            taskToEdit.setPriority(priorityCheckBox.isChecked());
            Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            taskToEdit.setDate(calendar);
            SaveTask saveTask = new SaveTask(UPDATE);
            saveTask.execute(taskToEdit);
            Intent returnIntent = new Intent();
            returnIntent.putExtra(TASK, taskToEdit);
            setResult(RESULT_OK, returnIntent);
            finish();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class SaveTask extends AsyncTask<Task, Void, Task> {
        private int action;

        SaveTask(int action) {
            this.action = action;
        }

        @Override
        protected Task doInBackground(Task... params){
            DBHelper dbHelper = new DBHelper(AddTaskActivity.this);
            switch (action) {
                case INSERT:
                    int id = dbHelper.add(params[0]);
                    params[0].setId(id);
                    return params[0];
                case UPDATE:
                    try {
                        dbHelper.update(params[0]);
                        return params[0];
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Task result) {
            if(result != null) {
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                if(action == INSERT) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(TASK, result);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        }
    }
}
