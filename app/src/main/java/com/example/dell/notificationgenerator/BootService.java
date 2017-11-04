package com.example.dell.notificationgenerator;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BootService extends IntentService {


    public BootService() {
        super("BootService");

    }



    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("Boot Service","Boot Service");


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Boot Notification")
                .setContentText("Boot Service");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());

    }


}
