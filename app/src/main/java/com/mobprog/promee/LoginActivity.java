package com.mobprog.promee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailEt, passwordEt;
    Button loginBtn;
    TextView forgotPasswordTv, signupTv;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialize component
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPasswordTv = findViewById(R.id.forgotPasswordTv);
        signupTv = findViewById(R.id.signupTv);

        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            gotoHome();
        }

        loginBtn.setOnClickListener(view -> {
            //progressBar.setVisibility(View.VISIBLE);
            String emailInput = emailEt.getText().toString();
            String passwordInput = passwordEt.getText().toString();

            if(emailInput.isEmpty() || passwordInput.isEmpty()){
                Toast.makeText(LoginActivity.this, "Missing email or password.", Toast.LENGTH_SHORT).show();
                return;
            }
            Login(emailInput,passwordInput);
        });
        signupTv.setOnClickListener(view -> {
            gotoSignUp();
        });
        forgotPasswordTv.setOnClickListener(view -> {
            gotoForgotPassword();
        });
    }

    public void Login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                            gotoHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void gotoSignUp(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    void gotoHome(){
        Intent intent =new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
    void gotoForgotPassword(){
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
    }
}