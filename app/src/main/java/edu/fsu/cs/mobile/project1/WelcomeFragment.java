package edu.fsu.cs.mobile.project1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment{

    Button guestButton;
    Button loginButton;
    Button signButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.welcome_fragment, container, false);

        guestButton = (Button) rootView.findViewById(R.id.button_guest);
        loginButton = (Button) rootView.findViewById(R.id.button_login);
        signButton = (Button) rootView.findViewById(R.id.button_signUp);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment fragment = new LoginFragment();
                String tag = LoginFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).addToBackStack("login").commit();
            }
        });


        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment fragment = new RegisterFragment();
                String tag = RegisterFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).addToBackStack("register").commit();
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyFragment fragment = new StudyFragment();
                String tag = StudyFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).addToBackStack("guest").commit();
            }
        });

        return rootView;
    }
}
