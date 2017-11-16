package com.example.todo.todo.helper;

/**
 * Created by amanj on 6/6/2017.
 */

public class Content {
    public String title,duedate,duetime,description;

    public Content(String title,String duedate,String duetime,String description)
    {
        this.title=title;
        this.duedate=duedate;
        this.duetime=duetime;
        this.description=description;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDuedate() {
        return duedate;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }






}
