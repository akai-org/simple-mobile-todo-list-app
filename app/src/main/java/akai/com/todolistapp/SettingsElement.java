package akai.com.todolistapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

abstract public class SettingsElement {
    private String name;
    private Context context;
    private Activity activity;

    SettingsElement(Context context, Activity activity){
        name = "TEST";
        this.context = context;
        this.activity = activity;
    }

    SettingsElement(String name, Context context, Activity activity){
        this.name = name;
        this.context = context;
        this.activity = activity;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Context getContext() {
        return context;
    }

    public void start(){ } //this method starts after onclick event

    protected void refreshActivity(){
        Intent refresh = new Intent(this.context, SettingsActivity.class);
        this.context.startActivity(refresh);
        this.activity.finish();
    }

}
