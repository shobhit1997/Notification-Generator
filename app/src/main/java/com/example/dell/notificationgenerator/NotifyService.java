package com.example.dell.notificationgenerator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

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
        /*if(notificationManager.getActiveNotifications())
        {
            count=0;
        }*/
                Date today = new Date(System.currentTimeMillis());
                int hours = today.getHours();
                int min = today.getMinutes();
                Log.i("Min:",min+"");
                if (min%2==0) {

                    count++;

                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentText("Body of notification"+count);
                    notification.setNumber(count);
                    Intent i = new Intent(this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pendingIntent);


                    notificationManager.notify(id++, notification.build());

                }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {

        id=100;
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("this is a ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Here is title");
        count=0;

        super.onCreate();

    }
}
