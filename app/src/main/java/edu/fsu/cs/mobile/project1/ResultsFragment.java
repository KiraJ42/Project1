package edu.fsu.cs.mobile.project1;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ResultsFragment extends Fragment{

    public String timeStop;
    Button StudyButton;
    Button SignUp;
    private TextView totalTime;
    private long time;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        timeStop = getArguments().getString("stopTime");
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.results_fragment, container, false);

        TextView mTextView = (TextView) rootView.findViewById(R.id.results);

        Bundle bundle = getArguments();

        timeStop = bundle.getString("stopTime");
        StudyButton = (Button) rootView.findViewById(R.id.study);
        SignUp = (Button) rootView.findViewById(R.id.signup);

        if (timeStop != null) {
            mTextView.setText("You Studied For \n" + timeStop);
        }

        SharedPreferences preferences = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE);
        Boolean LoggedIn = preferences.getBoolean("login", false);

        totalTime = (TextView) rootView.findViewById(R.id.total_time);

        time = preferences.getLong("time", 0);
        totalTime.setText("Total Time: " + timeFormatter(time));

        if (LoggedIn) {
            totalTime.setVisibility(View.VISIBLE);
        } else {
            totalTime.setVisibility(View.INVISIBLE);
        }

        if(!LoggedIn) SignUp.setVisibility(View.VISIBLE);

        StudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyFragment fragment = new StudyFragment();
                String tag = StudyFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).commit();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment fragment = new RegisterFragment();
                String tag = RegisterFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).commit();
            }
        });

        return rootView;
    }


    private String timeFormatter (long millisecs){
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisecs) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecs)),
                TimeUnit.MILLISECONDS.toSeconds(millisecs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecs)));
    }

}
