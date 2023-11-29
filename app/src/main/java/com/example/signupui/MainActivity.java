package com.example.signupui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView loginTv;
    EditText usernameInput, emailInput, passwordInput, cPasswordInput;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize components
        usernameInput = findViewById(R.id.usernameEt);
        emailInput = findViewById(R.id.emailEt);
        passwordInput = findViewById(R.id.passwordEt);
        cPasswordInput = findViewById(R.id.cPasswordEt);
        signUpBtn = findViewById(R.id.signUpBtn);

    }

    public void SignUp () {

    }

}