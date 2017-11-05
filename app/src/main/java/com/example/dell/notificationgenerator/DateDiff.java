package com.example.dell.notificationgenerator;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDiff {
    String date;
    public DateDiff(String date)
    {
        this.date=date;
    }
    public NewDate difference()
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(date);
            d2 = new Date(System.currentTimeMillis());
            //in milliseconds
            long diff = d1.getTime() - d2.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            return new NewDate(diffDays,diffHours,diffMinutes);

            /*System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");*/


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
