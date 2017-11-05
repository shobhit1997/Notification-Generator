package com.example.dell.notificationgenerator;

/**
 * Created by DELL on 11/5/2017.
 */

public class NewDate {
    
    long days;
    long mins;
    long hours;
    public NewDate(long days,long hours,long mins)
    {
        this.days=days;
        this.hours=hours;
        this.mins=mins;
    }
    public long getDays()
    {
        return days;
    }
    public long getMinutes()
    {
        return mins;
    }
    public long getHours()
    {
        return hours;
    }
    
}
