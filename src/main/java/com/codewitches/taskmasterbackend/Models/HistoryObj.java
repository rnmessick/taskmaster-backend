package com.codewitches.taskmasterbackend.Models;

import java.util.Date;

public class HistoryObj {
    String time;
    String action;

    public HistoryObj(String action){
        this.action = action;
        Date date = new Date();
        this.time = new Date(System.currentTimeMillis()).toString();
    }

    public HistoryObj(){}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String toString() {
        return String.format("%s @ %s", this.getAction(), this.getTime());
    }
}