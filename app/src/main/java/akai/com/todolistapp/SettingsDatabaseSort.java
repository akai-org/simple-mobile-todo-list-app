package akai.com.todolistapp;

import android.content.Context;
import android.content.Intent;

public class SettingsDatabaseSort extends SettingsElement {

    private Context context;

    SettingsDatabaseSort(Context context){
        super();
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        if(dbHelper.getSortmode() == 0){
            this.setName("Sort by date");
        } else {
            this.setName("Sort by priority");
        }
    }

    public void start(){
        DBHelper dbHelper = new DBHelper(context);
        if(dbHelper.getSortmode() == 0){
            dbHelper.setSortmodeDate();
            this.setName("Sort by priority");
        } else {
            dbHelper.setSortmodeKey();
            this.setName("Sort by date");
        }
        //Intent refresh = new Intent(this.context, SettingsActivity.class);
        //this.context.startActivity(refresh);
        //this.context.finish();
        //TODO refreshing - how to run finish() properly?
    }
}
