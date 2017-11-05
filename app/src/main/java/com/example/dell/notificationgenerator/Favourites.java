package com.example.dell.notificationgenerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    ArrayList<Event> fav_events;
    ArrayList<String> name;
    ArrayAdapter<String> adapter;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        fav_events=new ArrayList<Event>();
        name=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
        UpdateList();
        ListView fav=(ListView)findViewById(R.id.list_view_fav);
        fav.setAdapter(adapter);

        fav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }

    public void UpdateList()
    {

        DBHelper helper=new DBHelper(getApplicationContext());

        database=helper.getReadableDatabase();
        helper.createTable(database);
        Cursor c=database.rawQuery("SELECT * FROM fav_events",null);
        int nameIndex=c.getColumnIndex("name");
        int timeIndex=c.getColumnIndex("time");
        //c.moveToFirst();
        while (c.moveToNext())
        {
            Event obj=new Event(c.getString(nameIndex),c.getString(timeIndex));
            fav_events.add(obj);
            name.add(obj.getName());
            Log.i("Fav Events ", obj.getName());
            Log.i("Timings ", obj.getDate()+"");

        }
        c.close();
        adapter.notifyDataSetChanged();
    }
    public void clear(View view)
    {
        DBHelper helper=new DBHelper(getApplicationContext());
        database=helper.getReadableDatabase();
        //database.execSQL("DROP TABLE IF EXISTS fav_events");
        helper.deleteTable(database);
        name.clear();
        fav_events.clear();
        adapter.notifyDataSetChanged();
    }
}
