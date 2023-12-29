package com.mobprog.promee;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobprog.promee.service.AuthenticationService;

public class LoginActivity extends AppCompatActivity {
    EditText emailEt, passwordEt;
    Button loginBtn;
    TextView forgotPasswordTv, signupTv;
    AuthenticationService authService;
    MainActivity mainActivity;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialize component
        authService = new AuthenticationService(this);
        mainActivity = new MainActivity();

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPasswordTv = findViewById(R.id.forgotPasswordTv);
        signupTv = findViewById(R.id.signupTv);

    }
    @Override
    public void onStart() {
        super.onStart();
        CheckUser();
        loginBtn.setOnClickListener(view -> LogIn());
        signupTv.setOnClickListener(view -> gotoSignUp());
        forgotPasswordTv.setOnClickListener(view -> gotoForgotPassword());
    }
    void gotoSignUp(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    void gotoForgotPassword(){
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
    void CheckUser(){
        if(authService.CheckUserLoggedIn()){
            authService.gotoHome();
        }
    }
    void LogIn(){
        String emailInput = emailEt.getText().toString();
        String passwordInput = passwordEt.getText().toString();

        if(emailInput.isEmpty() || passwordInput.isEmpty()){
            Toast.makeText(LoginActivity.this, "Missing email or password.", Toast.LENGTH_SHORT).show();
        }else{
            authService.LogIn(emailInput,passwordInput);
        }


    }
}