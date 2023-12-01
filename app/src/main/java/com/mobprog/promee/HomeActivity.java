package com.mobprog.promee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    ImageView backbtn;
    LinearLayout profile, friends, groups, settings, help;
    Button logoutBtn;
    TextView usernameTv, emailtv;
    String username, email, userId;

    //Firebase Initialization;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference rootDtb, userDtb;

    //Data reader
    ValueEventListener readUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize components for navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu_icon);
        profile = findViewById(R.id.profile);
        friends = findViewById(R.id.friends);
        groups = findViewById(R.id.groups);
        settings = findViewById(R.id.settings);
        help = findViewById(R.id.help);
        logoutBtn = findViewById(R.id.logoutBtn);
        backbtn = findViewById(R.id.backbtn);

        //initialize components for content
        usernameTv = findViewById(R.id.userNameTv);
        emailtv = findViewById(R.id.emailTv);

        //authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            gotoLogin();
        }else{
            //display
            userId = currentUser.getUid();
        }

        //initialize components for database
        //realtime database
        rootDtb = FirebaseDatabase.getInstance().getReference();
        userDtb = rootDtb.child("users").child(userId);
        readUserData = new ValueEventListener() {
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
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        };

        userDtb.addValueEventListener(readUserData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menu.setOnClickListener(view ->
                 {openDrawer(drawerLayout);}
        );

        backbtn.setOnClickListener(view -> {
            closeDrawer(drawerLayout);
        });
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            userDtb.removeEventListener(readUserData);
            gotoLogin();
        });
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    void gotoLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}