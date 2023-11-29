package com.example.signupui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTv, userEmailtv;
    String username, email;
    Button logoutBtn;
    UserDataClass userData;

    //Firebase Initialization;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize components
        usernameTv = findViewById(R.id.userNameTv);
        userEmailtv = findViewById(R.id.userEmailTv);
        logoutBtn = findViewById(R.id.logoutBtn);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            gotoLogin();
        }else{
            //display
        }

        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            gotoLogin();
        });
    }
    void gotoLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}