package edu.fsu.cs.mobile.project1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResultsFragment extends Fragment{

    public String timeStop;
    Button StudyButton;

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

        if (timeStop != null) {
            mTextView.setText("You Studied For \n" + timeStop);
        }


        StudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyFragment fragment = new StudyFragment();
                String tag = StudyFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).commit();
            }
        });
        return rootView;
    }

}
