package com.example.signupui;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView loginTv;
    EditText usernameEt, emailEt, passwordEt, cPasswordEt;
    Button signUpBtn;
    String userNameInput, emailInput, passwordInput, cPasswordInput, userId, username, email;
    //Firebase initialization
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference rootDtb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize components
        usernameEt = findViewById(R.id.usernameEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        cPasswordEt = findViewById(R.id.cPasswordEt);
        signUpBtn = findViewById(R.id.signUpBtn);
        loginTv = findViewById(R.id.loginTv);

        mAuth = FirebaseAuth.getInstance();


        signUpBtn.setOnClickListener(view -> {
            //progressBar.setVisibility(View.VISIBLE);
            userNameInput = usernameEt.getText().toString();
            emailInput = emailEt.getText().toString();
            passwordInput = passwordEt.getText().toString();
            cPasswordInput = cPasswordEt.getText().toString();

            if(userNameInput.isEmpty() || emailInput.isEmpty() || passwordInput.isEmpty() || cPasswordInput.isEmpty()){
                Toast.makeText(MainActivity.this, "Some fields are empty.", Toast.LENGTH_SHORT).show();
            }else if(!passwordInput.equals(cPasswordInput)){
                Toast.makeText(MainActivity.this, "Password does not match.", Toast.LENGTH_SHORT).show();
            }else{

                SignUp(userNameInput, emailInput, passwordInput);
            }

        });
        loginTv.setOnClickListener(view -> {
            gotoLogin();
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            gotoHome();
        }


    }
    //Create user with username, email, and password
    public void SignUp (String username, String email, String password) {
        //UserDataClass userData = new UserDataClass(username,email,password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                userId = user.getUid();
                                rootDtb = FirebaseDatabase.getInstance().getReference();
                                writeNewUser(userId, userNameInput, emailInput);
                                // Now 'userId' contains the unique identifier for the newly created user
                                Toast.makeText(MainActivity.this, "User ID:"+userId, Toast.LENGTH_SHORT).show();
                            }
                            gotoHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
     void gotoHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    void gotoLogin(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void writeNewUser(String userId, String username, String email) {
        UserDataClass user = new UserDataClass(username, email);

        rootDtb.child("users").child(userId).setValue(user);
    }


}