package akai.com.todolistapp;

import android.content.Context;

abstract public class SettingsElement {
    private String name;
    protected Context context;

    SettingsElement(Context context){
        name = "TEST";
        this.context = context;
    }

    SettingsElement(String name, Context context){
        this.name = name;
        this.context = context;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void start(){

    }

}
