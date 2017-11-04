package com.example.dell.notificationgenerator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import static android.content.Context.ALARM_SERVICE;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: context method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("Boot Service","Boot Service");
        Intent background = new Intent(context, BootService.class);
        context.startService(background);


    }
}
