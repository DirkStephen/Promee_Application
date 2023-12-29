package com.mobprog.promee;

public class TaskDataClass {
    String task_name, task_note, task_date, task_start, task_end;

    public TaskDataClass(String task_name, String task_note, String task_date, String task_start, String task_end) {
        this.task_name = task_name;
        this.task_note = task_note;
        this.task_date = task_date;
        this.task_start = task_start;
        this.task_end = task_end;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_note() {
        return task_note;
    }

    public String getTask_date() {
        return task_date;
    }

    public String getTask_start() {
        return task_start;
    }

    public String getTask_end() {
        return task_end;
    }
}
