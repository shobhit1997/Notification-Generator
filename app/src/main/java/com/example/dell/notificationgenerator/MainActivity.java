package com.example.dell.notificationgenerator;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    ArrayList<Event> events;
    ArrayList<String> lists;
    ArrayList<String> fav;
    ArrayList<String> favTime;
    ArrayAdapter<String> adapter;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmService();
        events=new ArrayList<Event>();
        lists=new ArrayList<String>();
        fav=new ArrayList<>();
        favTime=new ArrayList<>();
        int i=18;
        lists.add("Sherlocked");
        lists.add("Bug Alert");
        lists.add("Errata");
        lists.add("Ovid");
        lists.add("Khoj");
        lists.add("Algorithematics");
        lists.add("Code Wars");
        lists.add("Knight Knitting");

        events.add(new Event("Sherlocked",i));
        events.add(new Event("Bug Alert",i+2));
        events.add(new Event("Errata",i+4));
        events.add(new Event("Ovid",i+6));
        events.add(new Event("Khoj",i+8));
        events.add(new Event("Algorithematics",i+10));
        events.add(new Event("Code Wars",i+12));
        events.add(new Event("Knight Knitting",i+14));
        adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        try {
             database =this.openOrCreateDatabase("FavEvents",Context.MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, time INT(2))");

        }catch (Exception e){}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //fav.add(events.get(i).getName());
                //favTime.add(events.get(i).getMins()+"");
                    insert(events.get(i).getName(),events.get(i).getMins());


                /*SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.dell.notificationgenerator",Context.MODE_PRIVATE);
                sharedPref.edit().putStringSet("events",new HashSet<String>(fav)).apply();
                sharedPref.edit().putStringSet("time",new HashSet<String>(favTime)).apply();
                Log.i("Fav Events Addition", new HashSet<String>(fav).toString());
                Log.i("Timings Addition" , new HashSet<String>(favTime).toString());*/



            }
        });






    }
    public void insert(String name , int time)
    {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("time", time);
        database.insert("events",null,values);
    }
    public void alarmService()
    {
        Intent alarm = new Intent(this, MyReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if(alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
        }
    }



    /*public void createNotification(View view)
    {
        Intent i=new Intent(this,NotifyService.class);
        startService(i);


            /*notification.setSmallIcon(R.mipmap.ic_launcher);
            notification.setTicker("this is a ticker");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle("Here is title");
            notification.setContentText("Body of notification");
            Intent i = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(100, notification.build());
        }*/

}

