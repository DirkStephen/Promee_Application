package com.example.signupui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTv, emailtv;
    String username, email, userId;
    Button logoutBtn;
    UserDataClass userData;

    //Firebase Initialization;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference rootDtb, userDtb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(this);
        //initialize components
        usernameTv = findViewById(R.id.userNameTv);
        emailtv = findViewById(R.id.emailTv);
        logoutBtn = findViewById(R.id.logoutBtn);

        //authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            gotoLogin();
        }else{
            //display
            userId = currentUser.getUid();
        }

        //realtime database
        rootDtb = FirebaseDatabase.getInstance().getReference();
        userDtb = rootDtb.child("users").child(userId);
        //read user details
        userDtb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDataClass userData = snapshot.getValue(UserDataClass.class);
                username = userData.getUsername();
                email = userData.getEmail();
                usernameTv.setText(username);
                emailtv.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

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