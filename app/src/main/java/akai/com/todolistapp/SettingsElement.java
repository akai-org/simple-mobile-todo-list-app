package akai.com.todolistapp;

abstract public class SettingsElement {
    private String name;

    SettingsElement(){
        name = "TEST";
    }

    SettingsElement(String name){
        this.name = name;
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
