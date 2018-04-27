package akai.com.todolistapp;

import java.io.Serializable;
import java.util.*;


/**
 * Created by Klaudia on 13-Nov-17.
 */

public class Task implements Serializable {
    private String title;
    private Calendar date;
    private Boolean status;
    private Boolean priority = false;
    private int id = -1; //used to identify tasks in database

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
        this.date = date;

    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getPriority() {
        return this.priority;
    }

    public int getId() {
        return this.id;
    }

    Task(String title, Calendar date, Boolean status) throws Exception {
        setTitle(title);
        setDate(date);
        setStatus(status);
    }

    public int daysToDeadline() {
        Calendar today = Calendar.getInstance();
        today = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        long diffInMillis = this.date.getTimeInMillis() - today.getTimeInMillis();
        return (int) (diffInMillis / (1000*3600*24));
    }
}
