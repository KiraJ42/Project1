package edu.fsu.cs.mobile.project1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button guestButton;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("App", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("App", MODE_PRIVATE).edit();
        editor.putBoolean("StudyMode", false);
        editor.commit();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        WelcomeFragment fragment = new WelcomeFragment();       //just open the welcome Fragment, no need to do anything else
        String tag = WelcomeFragment.class.getCanonicalName();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).commitAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            SharedPreferences preferences = getSharedPreferences("App", MODE_PRIVATE);
            if (mAccel > 5 && preferences.getBoolean("StudyMode", false)) {
                NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(this);

                builder.setAutoCancel(false);
                builder.setContentTitle("Hang on!");
                builder.setContentText("You're not done studying yet!");
                builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                builder.setOngoing(true);
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setPriority(Notification.PRIORITY_HIGH);
                builder.build();

                Notification notification = builder.getNotification();
                manager.notify(1, notification);
                //Toast.makeText(this, "MOTION DETECTED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }

}
