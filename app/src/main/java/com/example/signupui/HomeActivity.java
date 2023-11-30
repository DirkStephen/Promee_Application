package com.example.signupui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    ImageView backbtn;
    LinearLayout profile, friends, groups, settings, help;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialized components
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu_icon);
        profile = findViewById(R.id.profile);
        friends = findViewById(R.id.friends);
        groups = findViewById(R.id.groups);
        settings = findViewById(R.id.settings);
        help = findViewById(R.id.help);
        logout = findViewById(R.id.logout);
        backbtn = findViewById(R.id.backbtn);
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
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}