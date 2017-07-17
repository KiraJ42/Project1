package edu.fsu.cs.mobile.project1;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.FeatureGroupInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.GetChars;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private String email;
    private String password;
    private EditText emailEditText;
    private EditText passwordEditText;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = (EditText) rootView.findViewById(R.id.editText_email);
        passwordEditText = (EditText) rootView.findViewById(R.id.editText_password);
        final Button loginButton = (Button) rootView.findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (CheckIfValid(email, password)) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE).edit();

                    String checkEmail = sharedPreferences.getString("username", "username").toString();
                    String checkPassword = sharedPreferences.getString("password", "password").toString();

                    if (email.matches(checkEmail) && password.matches(checkPassword)){
                        editor.putBoolean("login", true);
                        editor.putString("username", email);
                        editor.putString("password",password);
                        editor.commit();

                        StudyFragment fragment = new StudyFragment();
                        String tag = StudyFragment.class.getCanonicalName();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment, tag).disallowAddToBackStack().commit();

                    } else {
                        emailEditText.setError("Incorrect or Unregistered Email");
                        passwordEditText.setError("Incorrect or Unregistered Password");
                    }

                }

            }
        });

        return rootView;
    }


    public Boolean CheckIfValid(String email, String password) {
        Boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Empty Email");
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Empty Password");
            valid = false;
        }

        return valid;
    }


}
