package com.mobprog.promee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu, backbtn;
    LinearLayout profile, friends, groups, settings, help;
    Button logoutBtn, cancelbtn;
    TextView usernameTv, emailtv;
    String username, email, userId;

    //Create Task
    Dialog dialog;
    EditText taskName, taskNote, date, startTime, endTime;
    Button dCancelBtn, dCreateBtn;


    FloatingActionButton fab;

    //Firebase Initialization;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference root, user_name;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyFragmentAdapter adapter;
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




        //dialog box
        fab = findViewById(R.id.fab);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.create_task_dialog);

        dCancelBtn = dialog.findViewById(R.id.dCancelBtn);
        dCreateBtn = dialog.findViewById(R.id.dCreateBtn);

        //create task
        taskName = dialog.findViewById(R.id.taskName);
        taskNote = dialog.findViewById(R.id.taskNote);
        date = dialog.findViewById(R.id.taskDate);
        startTime = dialog.findViewById(R.id.startTime);
        endTime = dialog.findViewById(R.id.endTime);

        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // Specify desired width
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Specify desired height
        dialog.getWindow().setAttributes(layoutParams);

        fab.setOnClickListener(view -> {
            dialog.show();
        });
        dCreateBtn.setOnClickListener(view -> {
            String task_name = taskName.getText().toString();
            String task_note = taskNote.getText().toString();
            String task_date = date.getText().toString();
            String task_start = startTime.getText().toString();
            String task_end = endTime.getText().toString();
            dialog.dismiss();
        });
        dCancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(dialog.getContext(),R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                },  year, month, day);
                datePickerDialog.show();
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(dialog.getContext(),R.style.DatePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(dialog.getContext(), R.style.DatePicker, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();

            }
        });


        //authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            gotoLogin();
        } else {
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
                usernameTv.setText(username);
                emailtv.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        };
        user_name.addValueEventListener(readUserData);

  //dialog
  //Forda tab bar
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        tabLayout.addTab(tabLayout.newTab().setText("To do"));
        tabLayout.addTab(tabLayout.newTab().setText("Doing"));
        tabLayout.addTab(tabLayout.newTab().setText("Done"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentAdapter(fragmentManager , getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        menu.setOnClickListener(view -> {
            openDrawer(drawerLayout);
        });

        backbtn.setOnClickListener(view -> {
            closeDrawer(drawerLayout);
        });
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            user_name.removeEventListener(readUserData);
            gotoLogin();
        });
        settings.setOnClickListener(view -> {
            gotoSettings();
        });
        help.setOnClickListener(view -> {
            gotoHelp();
        });
        groups.setOnClickListener(view ->{

            gotoGroups();
        });
        profile.setOnClickListener(view ->{
            gotoProfile();
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    void gotoLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
    void gotoSettings() {
        Intent i = new Intent(HomeActivity.this, SettingsPage.class);
        startActivity(i);
    }

    void gotoHelp() {
        Intent i = new Intent(HomeActivity.this, HelpPage.class);
        startActivity(i);
    }

    void gotoGroups() {
        Intent i = new Intent(HomeActivity.this, GroupPage.class);
        startActivity(i);
    }
    void gotoProfile(){
        Intent i = new Intent(HomeActivity.this, ProfilePage.class);
        startActivity(i);
    }

}