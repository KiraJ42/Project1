package edu.fsu.cs.mobile.project1;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private String name;
    private String email;
    private String password;

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        nameEditText = (EditText) rootView.findViewById(R.id.editText_name);
        emailEditText = (EditText) rootView.findViewById(R.id.editText_email);
        passwordEditText = (EditText) rootView.findViewById(R.id.editText_password);

        Button registerButton = (Button) rootView.findViewById(R.id.button_signUp);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText != null && emailEditText != null && passwordEditText != null) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("App", Context.MODE_PRIVATE).edit();

                    name = nameEditText.getText().toString();
                    email = emailEditText.getText().toString();
                    password = passwordEditText.getText().toString();

                    if (CheckIfValid(name, email, password)) {
                        editor.putString("username", email);
                        editor.putString("password", password);
                        editor.putBoolean("login", true);
                        editor.commit();

                        StudyFragment fragment = new StudyFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment).disallowAddToBackStack().commit();

                    } else {
                        Toast.makeText(getActivity(), "Incorrect Information", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return rootView;
    }

    private Boolean CheckIfValid(String name, String email, String password) {
        Boolean valid = true;
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is Empty");
            valid = false;
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is Empty");
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is Empty");
            valid = false;
        }

        return valid;
    }

}
