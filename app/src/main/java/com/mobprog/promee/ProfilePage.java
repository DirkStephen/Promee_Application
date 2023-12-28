package com.mobprog.promee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {
    String username, email, userId;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference root, user_name;
    private Button button;
    ValueEventListener readUserData;
    TextView usernameTV, emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button = (Button) findViewById(R.id.button);
        usernameTV = findViewById(R.id.usernameTV);
        emailTV = findViewById(R.id.emailTV);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }
        });
        //authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){

        }else{
            //display
            userId = currentUser.getUid();
        }
        //initialize components for database
        //realtime database
        root = FirebaseDatabase.getInstance().getReference();
        user_name = root.child("users").child(userId);
        readUserData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDataClass userData = snapshot.getValue(UserDataClass.class);
                username = userData.getUsername();
                email = userData.getEmail();
                usernameTV.setText(username);
                emailTV.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        };
        user_name.addValueEventListener(readUserData);
    }
    public void openEditProfile(){
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
}