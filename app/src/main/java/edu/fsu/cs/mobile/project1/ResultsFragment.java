package edu.fsu.cs.mobile.project1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultsFragment extends Fragment{

    public String timeStop;

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

        if (timeStop != null) {
            mTextView.setText("You Studied For \n" + timeStop);
        }


        return rootView;
    }

}
