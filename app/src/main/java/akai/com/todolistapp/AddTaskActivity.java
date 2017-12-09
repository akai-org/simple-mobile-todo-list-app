package akai.com.todolistapp;

import android.content.Intent;
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
            try {
                DBHelper dbHelper = new DBHelper(this);
                taskToEdit = dbHelper.get(id);
                nameEditText.setText(taskToEdit.getTitle());
                priorityCheckBox.setChecked(taskToEdit.getPriority());
                Calendar calendar = taskToEdit.getDate();
                datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            } catch (Exception ex) {

            }
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
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.add(task);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
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
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.update(taskToEdit);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
