package com.example.schoolroutineapp;

import java.util.Calendar;

public class ActivityItem {

    private String title;
    private String startTime;
    private String endTime;
    private Calendar date;
    private boolean completed;

    public ActivityItem(String title, String startTime, String endTime, Calendar date) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Calendar getDate() {
        return date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
