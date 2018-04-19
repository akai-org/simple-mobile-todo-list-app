package akai.com.todolistapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TrialOption extends SettingsElement {
    TrialOption(Context context){
        super("TrialOption",context);
    }

    public void start(){
        Log.d("TrialOption", "Hello there");
        Toast.makeText(this.context,"Hello there!",Toast.LENGTH_SHORT).show();
    }
}
