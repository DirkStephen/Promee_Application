package com.mobprog.promee.model;

public class TaskDataClass {
    String tname, tdate, tstart, tend, tnote;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public TaskDataClass(){

    }

    public TaskDataClass(String tname, String tdate, String tstart, String tend, String tnote) {
        this.tname = tname;
        this.tdate = tdate;
        this.tstart = tstart;
        this.tend = tend;
        this.tnote = tnote;
    }

    public String getTname() {
        return tname;
    }

    public String getTdate() {
        return tdate;
    }

    public String getTstart() {
        return tstart;
    }

    public String getTend() {
        return tend;
    }

    public String getTnote() {
        return tnote;
    }
}
