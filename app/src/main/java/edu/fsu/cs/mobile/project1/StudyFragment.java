package edu.fsu.cs.mobile.project1;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class StudyFragment extends Fragment{

    ToggleButton STUDY;
    TextView TIMER;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, milliSeconds;
    public long Total;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.study_fragment, container, false);

        STUDY = (ToggleButton) rootView.findViewById(R.id.start_button);
        TIMER = (TextView) rootView.findViewById(R.id.timer);

        handler = new Handler();

        STUDY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startTimer();
                }else {
                    stopTimer();

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
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    public void stopTimer(){
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);      //stops the timer and displays the most recent time
    }

    public Runnable runnable = new Runnable() {   //code from: http://www.android-examples.com/android-create-stopwatch-example-tutorial-in-android-studio/
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis()-StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) UpdateTime/1000;
            Minutes = Seconds/60;
            Seconds = Seconds%60;
            milliSeconds = (int) (UpdateTime%1000);


            TIMER.setText("0:" + Minutes +":" + Seconds);
            handler.postDelayed(this, 0);
        }
    };
}
