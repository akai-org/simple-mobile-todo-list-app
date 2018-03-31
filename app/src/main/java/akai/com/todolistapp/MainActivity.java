package akai.com.todolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    static final int ADD_TASK_REQUEST = 1;

    private Button saveButton;
    private Button writeButton;
    private Button recButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //write();
            }
        });

        writeButton = findViewById(R.id.button2);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                read();
            }
        });

        recButton = findViewById(R.id.button3);
        //recButton.setOnClickListener((v) -> {});
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

            }
        }
    }

    private Task read(){
        //return task
        return null;
    }

    private void write(Task c){
        DBHelper db = new DBHelper(this);
        db.add(c);
    }

    public void openAddTaskActivity(View view){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void openRecyclerView(View view){
        Intent intent = new Intent(this, RecyclerViewBackground.class);
        startActivity(intent);
    }
}
