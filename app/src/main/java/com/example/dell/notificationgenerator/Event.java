package com.example.dell.notificationgenerator;

/**
 * Created by DELL on 11/5/2017.
 */

public class Event {

     String name;
    String date;
    int notiId;
    public Event(String name, String date)
    {
        this.date=date;
        this.name=name;
        this.notiId=0;
    }
    public Event(String name, String date , int notiId)
    {
        this.date=date;
        this.name=name;
        this.notiId=notiId;
    }
    public String  getName()
    {
        return name;
    }
    public String getDate()
    {
        return date;
    }
    public int getNotiId()
    {
        return notiId;
    }
}
