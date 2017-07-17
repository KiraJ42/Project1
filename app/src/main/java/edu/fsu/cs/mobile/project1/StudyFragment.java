package edu.fsu.cs.mobile.project1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;

public class StudyFragment extends Fragment{

    ToggleButton STUDY;
    TextView TIMER;
    CountDownTimer COUNT;
    ProgressBar BAR;
    EditText MINUTETEXT;

    long startTime = 60000, inMilli;
    public long time;
    int prog;
    Bundle bundle;

    private int menuLoginId = 0;
    private int menuLogoutId = 1;
    private int menuRegisterId = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SharedPreferences preferences = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE);
        Boolean loggedIn = preferences.getBoolean("login", false);

        MenuItem logout, login, register;

        if (loggedIn == true) {
            logout = menu.add(R.string.logout);
            menuLogoutId = logout.getItemId();
            logout.setIcon(R.drawable.icon_stop);
        } else {
            register = menu.add(R.string.register);
            login = menu.add(R.string.login);
            menuLoginId = login.getItemId();
            menuRegisterId = register.getItemId();
            register.setIcon(R.drawable.icon_star);
            login.setIcon(R.drawable.icon_start);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE).edit();

        if (menuLoginId == item.getItemId()){
            LoginFragment fragment = new LoginFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment).addToBackStack("login").commit();
        }

        if (menuLogoutId == item.getItemId()) {
            editor.remove("login");
            editor.commit();
            Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
        }

        if (menuRegisterId == item.getItemId()) {
            RegisterFragment fragment = new RegisterFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment).disallowAddToBackStack().commit();
        }

        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Intent intent = new Intent(getContext(), ScreenDetectService.class);


        final View rootView = inflater.inflate(R.layout.study_fragment, container, false);

        STUDY = (ToggleButton) rootView.findViewById(R.id.start_button);
        TIMER = (TextView) rootView.findViewById(R.id.timer);
        BAR = (ProgressBar) rootView.findViewById(R.id.progressCircle);
        MINUTETEXT = (EditText) rootView.findViewById(R.id.editMinute);

        STUDY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    SharedPreferences.Editor editor = getActivity()
                            .getSharedPreferences("App", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("StudyMode", true);
                    editor.commit();

                    if (!MINUTETEXT.getText().toString().isEmpty()){
                        prog = Integer.parseInt(MINUTETEXT.getText().toString().trim());
                    }
                    else {
                        prog = 1;
                    }

                    startTime = prog * 60 * 1000;
                    BAR.setMax((int)startTime / 1000);
                    BAR.setProgress((int)startTime / 1000);
                    MINUTETEXT.setEnabled(false);
                    startTimer();
                    getContext().startService(intent);
                }else {

                    SharedPreferences.Editor editor = getActivity()
                            .getSharedPreferences("App", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("StudyMode", false);
                    editor.commit();

                    time = startTime - inMilli; //determine time in Milliseconds

                    getContext().stopService(intent);       //SERVICE STOPS RUNNING WHEN RESULTS NEED TO BE DISPLAYED
                    ResultsFragment fragment = new ResultsFragment();       //if studying toggle button is "off" stop the time and go to the data fragment

                    bundle = new Bundle();
                    bundle.putString("stopTime", timeFormatter(time));  //bundle for time spent


                    fragment.setArguments(bundle);              //if studying toggle button is "off" stop the time and go to the data fragment

                    COUNT.cancel();
                    String tag = ResultsFragment.class.getCanonicalName();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).addToBackStack("results").commitAllowingStateLoss();
                }
            }
        });

        return rootView;
    }

    public void startTimer(){
        COUNT = new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                inMilli = millisUntilFinished;  //countdown in milliseconds
                TIMER.setText(timeFormatter(millisUntilFinished));
                BAR.setProgress((int)(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                PendingIntent contentIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, new Intent(),PendingIntent.FLAG_UPDATE_CURRENT );
                NotificationManager manage = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(getActivity().getApplicationContext());

                builder.setAutoCancel(true);
                builder.setContentTitle("Congratulations");
                builder.setContentText("You've Reached Your Study Goal!");
                builder.setSmallIcon(R.drawable.icon_star);
                builder.setContentIntent(contentIntent);
                builder.setOngoing(false);
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setPriority(Notification.PRIORITY_HIGH);
                builder.build();

                Notification note = builder.getNotification();
                manage.notify(1, note);

                inMilli = 0;

                STUDY.setChecked(false);
            }
        }.start();
    }

    private String timeFormatter (long millisecs){
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisecs) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecs)),
                TimeUnit.MILLISECONDS.toSeconds(millisecs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecs)));
    }
}
