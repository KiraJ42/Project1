package edu.fsu.cs.mobile.project1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final int NOTIFICATION_ID = 1;
        final int NOTIFICATION_ID2 = 2;
        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            NotificationManager mNotificationManger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Woah There!")
                    .setContentText("Put down your phone and keep studying!")
                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .build();
            mNotificationManger.notify(NOTIFICATION_ID, notification);
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            //this other thing
        }
    }
}
