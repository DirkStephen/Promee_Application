package com.mobprog.promee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobprog.promee.service.AuthenticationService;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView loginTv;
    EditText usernameEt, emailEt, passwordEt, cPasswordEt;
    Button signUpBtn;
    String userNameInput, emailInput, passwordInput, cPasswordInput;
    AuthenticationService authService;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{8,}" +                // at least 8 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize components
        authService = new AuthenticationService(this);

        usernameEt = findViewById(R.id.usernameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        cPasswordEt = findViewById(R.id.cPasswordEt);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginTv = findViewById(R.id.loginTv);



    }
    @Override
    public void onStart() {
        super.onStart();
        CheckUser();
        loginTv.setOnClickListener(view -> authService.gotoLogin());
        signUpBtn.setOnClickListener(view -> SignUp());
    }
    void SignUp(){
        userNameInput = usernameEt.getText().toString();
        emailInput = emailEt.getText().toString();
        passwordInput = passwordEt.getText().toString();
        cPasswordInput = cPasswordEt.getText().toString();


        if(userNameInput.isEmpty() || emailInput.isEmpty() || passwordInput.isEmpty() || cPasswordInput.isEmpty()){
            Toast.makeText(MainActivity.this, "Some fields are empty.", Toast.LENGTH_SHORT).show();
        }else if(!passwordInput.equals(cPasswordInput)) {
            Toast.makeText(MainActivity.this, "Password does not match.", Toast.LENGTH_SHORT).show();
        }else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Toast.makeText(MainActivity.this, "Password must have special characters and be 8 characters long.", Toast.LENGTH_SHORT).show();
        }
         //RegEx (/^(?=.*\d)(?=.*[A-Z])([@$%&#])[0-9a-zA-Z]{4,}$/)
        //Pass should contain one digit, one spec char, one uppercase char, and 8 chars long
        else{
            authService.SignUp(userNameInput, emailInput, passwordInput);
        }
    }

    void CheckUser(){
        if(authService.CheckUserLoggedIn()){
            authService.gotoHome();
        }

    }


}