package akai.com.todolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

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
