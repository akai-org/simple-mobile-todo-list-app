package akai.com.todolistapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class SettingsDatabaseSort extends SettingsElement {

    SettingsDatabaseSort(Context context, Activity activity){
        super(context,activity);
        DBHelper dbHelper = new DBHelper(this.getContext());
        if(dbHelper.getSortmode() == 0){
            this.setName("Sort by date");
        } else {
            this.setName("Sort by priority");
        }
    }

    public void start(){
        DBHelper dbHelper = new DBHelper(this.getContext());
        if(dbHelper.getSortmode() == 0){
            dbHelper.setSortmodeDate();
            this.setName("Sort by priority");
        } else {
            dbHelper.setSortmodePriority();
            this.setName("Sort by date");
        }
        this.refreshActivity();
    }
}
