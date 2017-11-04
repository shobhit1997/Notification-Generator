package com.example.dell.notificationgenerator;

/**
 * Created by DELL on 11/5/2017.
 */

public class Event {

     String name;
    int mins;
    public Event(String name, int mins)
    {
        this.mins=mins;
        this.name=name;
    }
    public String  getName()
    {
        return name;
    }
    public int getMins()
    {
        return mins;
    }
}
