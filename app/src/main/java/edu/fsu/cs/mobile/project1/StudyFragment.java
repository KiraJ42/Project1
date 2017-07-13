package edu.fsu.cs.mobile.project1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class StudyFragment extends Fragment{

    ToggleButton STUDY;
    TextView TIMER;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int seconds, Minutes, milliSeconds;
    CountDownTimer COUNT;
    Button UP, DOWN;
    TextView GOAL, goaltext;

    long startTime;
    int Seconds, Minutes, milliSeconds;
    public long Total;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Intent intent = new Intent(getContext(), ScreenDetectService.class);


        final View rootView = inflater.inflate(R.layout.study_fragment, container, false);

        STUDY = (ToggleButton) rootView.findViewById(R.id.start_button);
        TIMER = (TextView) rootView.findViewById(R.id.timer);
        GOAL = (TextView) rootView.findViewById(R.id.goal);
        UP = (Button) rootView.findViewById(R.id.inc);
        DOWN = (Button) rootView.findViewById(R.id.dec);
        TIMER.setVisibility(View.INVISIBLE);
        goaltext = (TextView) rootView.findViewById(R.id.goal_text);

        UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(GOAL.getText().toString());
                if(value < 60)
                    value += 1;
                GOAL.setText(String.valueOf(value));
            }
        });

        DOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(GOAL.getText().toString());
                if(value > 0)
                    value -= 1;
                GOAL.setText(String.valueOf(value));
            }
        });

        STUDY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String text = GOAL.getText().toString();
                    startTime = ((Long.parseLong(text) * 60)*1000);

                    TIMER.setVisibility(View.VISIBLE);
                    startTimer();


                    GOAL.setVisibility(View.INVISIBLE);
                    UP.setVisibility(View.INVISIBLE);
                    DOWN.setVisibility(View.INVISIBLE);
                    goaltext.setVisibility(View.INVISIBLE);
                }else {

                    ResultsFragment fragment = new ResultsFragment();       //if studying toggle button is "off" stop the time and go to the data fragment

                    ResultsFragment fragment = new ResultsFragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("stopTime", "0:" + Minutes + ":" + Seconds);

                    fragment.setArguments(bundle);              //if studying toggle button is "off" stop the time and go to the data fragment


                    String tag = ResultsFragment.class.getCanonicalName();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).commit();
                }

            }
        });

        return rootView;
    }

    public void startTimer(){

        COUNT = new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                TIMER.setText(String.valueOf((millisUntilFinished/1000)/60)+":"+String.valueOf((millisUntilFinished/1000)%60));

            }

            @Override
            public void onFinish() {

                PendingIntent contentIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, new Intent(),PendingIntent.FLAG_UPDATE_CURRENT );
                NotificationManager manage = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(getActivity().getApplicationContext());

                builder.setAutoCancel(false);
                builder.setContentTitle("Congratulations");
                builder.setContentText("You've Reached Your Study Goal!");
                builder.setSmallIcon(R.drawable.gold_star);
                builder.setContentIntent(contentIntent);
                builder.setOngoing(true);
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setPriority(Notification.PRIORITY_HIGH);
                builder.build();

                Notification note = builder.getNotification();
                manage.notify(1, note);

                TIMER.setVisibility(View.INVISIBLE);

                GOAL.setVisibility(View.VISIBLE);
                UP.setVisibility(View.VISIBLE);
                DOWN.setVisibility(View.VISIBLE);
                goaltext.setVisibility(View.VISIBLE);

                STUDY.setChecked(false);

            }
        }.start();
    }
}
