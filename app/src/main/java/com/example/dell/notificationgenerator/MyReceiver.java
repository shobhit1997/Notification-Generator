package com.example.dell.notificationgenerator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {



            Intent background = new Intent(context, NotifyService.class);
            context.startService(background);

    }

}
