package com.example.dell.notificationgenerator;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Toast;

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
        Date today = new Date(System.currentTimeMillis());
        ArrayList<String> dates=new ArrayList<String>();
         for(int i=0;i<=7;i++)
         {
             String s=(today.getMonth()+1)+"/"+today.getDate()+"/"+(today.getYear()+1900)+" "+today.getHours()+":"+((today.getMinutes()+16+i)%60)+":"+today.getSeconds();
             dates.add(s);
         }
        lists.add("Sherlocked");
        lists.add("Bug Alert");
        lists.add("Errata");
        lists.add("Ovid");
        lists.add("Khoj");
        lists.add("Algorithematics");
        lists.add("Code Wars");
        lists.add("Knight Knitting");
        int i=0;
        events.add(new Event("Sherlocked",dates.get(i++)));
        events.add(new Event("Bug Alert",dates.get(i++)));
        events.add(new Event("Errata",dates.get(i++)));
        events.add(new Event("Ovid",dates.get(i++)));
        events.add(new Event("Khoj",dates.get(i++)));
        events.add(new Event("Algorithematics",dates.get(i++)));
        events.add(new Event("Code Wars",dates.get(i++)));
        events.add(new Event("Knight Knitting",dates.get(i++)));
        adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //fav.add(events.get(i).getName());
                //favTime.add(events.get(i).getMins()+"");
                if(ifExixts(events.get(i).getName(),events.get(i).getDate()))
                {
                    Toast.makeText(MainActivity.this,"Already Added as Fav", Toast.LENGTH_SHORT).show();
                }
                else {
                    insert(events.get(i).getName(), events.get(i).getDate());
                }
                Intent intent=new Intent(MainActivity.this,EventActivity.class);
                intent.putExtra("event",events.get(i).getName());
                startActivity(intent);





            }
        });






    }
    public void insert(String name , String date)
    {
        DBHelper helper=new DBHelper(getApplicationContext());
        database=helper.getWritableDatabase();
        helper.createTable(database);
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("time", date);
        values.put("notiId", 0);
        database.insert("fav_events",null,values);
        Toast.makeText(MainActivity.this,"Added as Fav", Toast.LENGTH_SHORT).show();
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



    public void showFav(View view)
    {
        Intent i=new Intent(this,Favourites.class);
        startActivity(i);
        }

    public boolean ifExixts(String name , String date)
    {
       DBHelper helper=new DBHelper(getApplicationContext());
        database= helper.getReadableDatabase();
        helper.createTable(database);
        String[] projection = {"name","time","notiId"};
        String selection = "name" + " = ?";
        String[] selectionArgs = { name };
        Cursor cursor = database.query(
                "fav_events",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }

    }

}

