package edu.fsu.cs.mobile.project1;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.content.BroadcastReceiver;
import android.widget.Toast;


public class ScreenDetectService extends Service {
    private PhoneReceiver mReceiver;
    private IBinder mScreenBinder = new ScreenDetectServiceBinder();
    public ScreenDetectService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mScreenBinder;
    }
    //Binding might be unnecessary, we'll see
    public class ScreenDetectServiceBinder extends Binder{
        ScreenDetectService getService(){
            return ScreenDetectService.this;
        }
    }
    @Override
    public void onCreate(){
        mReceiver = new PhoneReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
    }
    @Override
    public void onDestroy(){
        unregisterReceiver(mReceiver);
    }
}
