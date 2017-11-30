package akai.com.todolistapp;

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

    private EditText nameEditText;
    private DatePicker datePicker;
    private CheckBox priorityCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        priorityCheckBox = (CheckBox) findViewById(R.id.priorityCheckBox);
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
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
}
