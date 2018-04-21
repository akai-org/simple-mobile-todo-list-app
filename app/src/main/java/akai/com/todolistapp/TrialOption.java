package akai.com.todolistapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TrialOption extends SettingsElement {
    TrialOption(Context context, Activity activity){
        super("TrialOption",context,activity);
    }

    public void start(){
        Log.d("TrialOption", "Hello there");
        Toast.makeText(this.getContext(),"Hello there!",Toast.LENGTH_SHORT).show();
    }
}
