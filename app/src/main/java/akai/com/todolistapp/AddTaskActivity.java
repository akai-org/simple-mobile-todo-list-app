package akai.com.todolistapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText nameEditText;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = nameEditText.getText().toString();
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                try {
                    Task task = new Task(taskName, calendar, false);
                    DBHelper dbHelper = new DBHelper(AddTaskActivity.this);
                    dbHelper.add(task);
                } catch(Exception ex) {
                    Toast.makeText(AddTaskActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
