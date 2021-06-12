package com.android.finalproject;

public class SelectedDate {
    private String year, month, date, time, type;

    public SelectedDate(String year, String month, String date, String time, String type) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public void setAll(String year, String month, String date, String time, String type) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() { return type; }

}
