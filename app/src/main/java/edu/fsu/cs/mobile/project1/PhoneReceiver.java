package edu.fsu.cs.mobile.project1;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

public class PhoneReceiver extends BroadcastReceiver {

    private ActivityManager activityManager;
    private List<ActivityManager.RunningAppProcessInfo> taskInfos;
    private ActivityManager.RunningAppProcessInfo task;
    //ComponentName name;

    @Override
    public void onReceive(Context context, Intent intent) {
        final int NOTIFICATION_ID = 1;
        final int NOTIFICATION_ID2 = 2;
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //taskInfos = activityManager.getRunningAppProcesses();
        //task = taskInfos.get(0);
        String name = activityManager.getRunningAppProcesses().get(0).processName;

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

            if (!name.equals("edu.fsu.cs.mobile.project1"))
            {
                Notification noti = new Notification.Builder(context)
                        .setContentTitle("Hold up!")
                        .setContentText("Get back to studying!")
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .build();
                mNotificationManger.notify(NOTIFICATION_ID, noti);
            }
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            //this other thing
        }
    }
}
