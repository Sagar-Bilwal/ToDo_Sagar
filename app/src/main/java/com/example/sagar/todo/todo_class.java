package com.example.sagar.todo;

/**
 * Created by SAGAR on 01-03-2018.
 */

public class todo_class
{
    private String time;
    private String task;
    private long id;
    public todo_class(String time,String task)
    {
        this.time = time;
        this.task = task;
        this.id=-1;
    }
    public todo_class(String time,String task,long id)
    {
        this.time = time;
        this.task = task;
        this.id=id;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
