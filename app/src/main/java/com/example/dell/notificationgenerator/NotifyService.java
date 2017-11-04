package com.example.dell.notificationgenerator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

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

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {



        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       ArrayList<Event> events=UpdateList();
                Date today = new Date(System.currentTimeMillis());
                int hours = today.getHours();
                int min = today.getMinutes();
                Log.i("Min:",min+"");
        /*SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.dell.notificationgenerator",Context.MODE_PRIVATE);
        Set<String> fav1=sharedPref.getStringSet("events",new HashSet<String>());
        Set<String> favTime1=sharedPref.getStringSet("time",new HashSet<String>());
        Log.i("Fav Events Read", fav1.toString());
        Log.i("Timings Read", favTime1.toString());

        Iterator<String> nameItr=fav1.iterator();
        Iterator<String> timeItr=favTime1.iterator();*/
        int size=events.size();
        for (int i=0;i<size;i++)
        {

            Event event=events.get(i);
            Log.i("Fav Events ", event.getName());
            Log.i("Timings ", event.getMins()+"");
            if (min == event.getMins()) {

                count++;
                notification.setContentTitle(event.getName());
                notification.setWhen(System.currentTimeMillis());
                notification.setContentText("Event is going to begin" + count);
                notification.setNumber(count);
                Intent call = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, call, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);


                notificationManager.notify(id++, notification.build());

            }
        }


        return super.onStartCommand(intent, flags, startId);
    }
    public ArrayList<Event> UpdateList()
    {
       ArrayList<Event> events=new ArrayList<Event>();
        SQLiteDatabase database =this.openOrCreateDatabase("FavEvents",Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, time INT(2))");
        Log.i("Running","true");
        Cursor c=database.rawQuery("SELECT * FROM events",null);
        int nameIndex=c.getColumnIndex("name");
        int timeIndex=c.getColumnIndex("time");
        //c.moveToFirst();
        while (c.moveToNext())
        {
            Event obj=new Event(c.getString(nameIndex),c.getInt(timeIndex));
            events.add(obj);
            //Log.i("Fav Events ", obj.getName());
            //Log.i("Timings ", obj.getMins()+"");

        }
        c.close();
        return events;
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
