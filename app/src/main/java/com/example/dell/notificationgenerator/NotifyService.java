package com.example.dell.notificationgenerator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;

import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


public class NotifyService extends Service {
    static NotificationCompat.Builder notification;
    static int id;
    static int count;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {



        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       ArrayList<Event> events=UpdateList();
                Date today = new Date(System.currentTimeMillis());
                int hours = today.getHours();
                int min = today.getMinutes();
                int day = today.getMinutes();

        int size=events.size();
        for (int i=0;i<size;i++)
        {

            Event event=events.get(i);
            Log.i("Fav Events ", event.getName());
            Log.i("Timings ", event.getDate());
            String event_date=event.getDate();
            NewDate d3=new DateDiff(event_date).difference();
            if (d3.getDays()==0&& d3.getHours()==0&&d3.getMinutes()==15) {

                notification.setContentTitle(event.getName());
                notification.setWhen(System.currentTimeMillis());
                notification.setContentText("Event is going to begin in 15 mins");
                notification.setColor(getResources().getColor(R.color.colorGreen));
                Intent call = new Intent(this,EventActivity.class);
                call.putExtra("event",event.getName());
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, call, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);

                updateDB(event.getName(),id);
                notificationManager.notify(id++, notification.build());

            }
            else if (d3.getDays()==0&& d3.getHours()==0&&d3.getMinutes()==3) {

                notification.setContentTitle(event.getName());
                notification.setWhen(System.currentTimeMillis());
                notification.setContentText("Event is going to begin");
                notification.setColor(getResources().getColor(R.color.colorRed));
                notification.setPriority(Notification.PRIORITY_MAX);
                Intent call = new Intent(this,EventActivity.class);
                call.putExtra("event",event.getName());
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, call, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                deleteRow(event.getName());
                notificationManager.notify(event.getNotiId(), notification.build());



            }
        }


        return super.onStartCommand(intent, flags, startId);
    }
    public ArrayList<Event> UpdateList()
    {
       ArrayList<Event> events=new ArrayList<Event>();
       DBHelper helper=new DBHelper(getApplicationContext());
        SQLiteDatabase database=helper.getReadableDatabase();
        helper.createTable(database);
        Cursor c=database.rawQuery("SELECT * FROM fav_events",null);
        int nameIndex=c.getColumnIndex("name");
        int timeIndex=c.getColumnIndex("time");
        int NotiIndex=c.getColumnIndex("notiId");
        //c.moveToFirst();
        while (c.moveToNext())
        {
            Event obj=new Event(c.getString(nameIndex),c.getString(timeIndex),c.getInt(NotiIndex));
            events.add(obj);
            //Log.i("Fav Events ", obj.getName());
            //Log.i("Timings ", obj.getMins()+"");

        }
        c.close();
        return events;
    }
    public void updateDB(String event,int id)
    {
        DBHelper helper=new DBHelper(getApplicationContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        helper.createTable(database);
        ContentValues values = new ContentValues();
        values.put("notiId",id );

// Which row to update, based on the title
        String selection = "name" + " LIKE ?";
        String[] selectionArgs = { event };

        int count = database.update(
                "fav_events",
                values,
                selection,
                selectionArgs);
    }
    public void deleteRow(String event)
    {
        DBHelper helper=new DBHelper(getApplicationContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        helper.createTable(database);

        String selection = "name" + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { event };
// Issue SQL statement.
        database.delete("fav_events", selection, selectionArgs);
    }

    @Override
    public void onCreate() {

        id=100;
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("this is a ticker");
        notification.setWhen(System.currentTimeMillis());

        count=0;

        super.onCreate();

    }
}
