package akai.com.todolistapp;

import java.util.*;


/**
 * Created by Klaudia on 13-Nov-17.
 */

public class Task {
    private String title;
    private Calendar date;
    private Boolean status;

    public void setTitle(String title) throws Exception{
        int titleLength = title.length();

        if(titleLength == 0) {
            throw new Exception("Too short title!");
        }
        else if(titleLength > 40) {
            throw new Exception("Your title must have less than 40 characters!");
        }
        else if (titleLength > 0 && titleLength < 40) {
            this.title = title;
        }
    }

    public void setDate(Calendar date) throws Exception {
        if (date == null) throw new Exception("Incorrect date");
        Calendar currentDate = Calendar.getInstance();

    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public Calendar getDate() {
        return this.date;
    }

    public Boolean getStatus() {
        return this.status;
    }

    Task(String title, Calendar date, Boolean status) throws Exception {
        setTitle(title);
        setDate(date);
        setStatus(status);
    }

}
