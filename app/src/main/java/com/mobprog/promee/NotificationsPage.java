package com.mobprog.promee;

//import static com.app.promee.SettingsPage.aSwitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class NotificationsPage extends AppCompatActivity {

    private TextView notificationsText;

    private Switch switcherVibrate;
    private Switch switcherTime;
    private Switch switcherTask;
    private Switch switcherRequest;
    private Switch switcherGroup;
    private TextView textSound;
    private ImageButton exitButton;

    private Switch switchesMode1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_page);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean nightModeEnabled = sharedPreferences.getBoolean("nightModeEnabled", false);

        ConstraintLayout constraintLayout = findViewById(R.id.notificationsContent);
        notificationsText = findViewById(R.id.aboutTextView);
        switcherVibrate = findViewById(R.id.vibrateSwitch);
        textSound = findViewById(R.id.soundTextView);
        switcherTime = findViewById(R.id.timeSwitch);
        switcherTask = findViewById(R.id.taskSwitch);
        switcherRequest = findViewById(R.id.requestSwitch);
        switcherGroup = findViewById(R.id.groupSwitch);
        exitButton = findViewById(R.id.exit_button);
        switchesMode1 = findViewById(R.id.vibrateSwitch);

        int backgroundColor = nightModeEnabled ? Color.BLACK : Color.parseColor("#FFA500");
        notificationsText.setBackgroundColor(backgroundColor);
        int back = nightModeEnabled ? R.drawable.exitbutton_notifs_white : R.drawable.exitbutton_notifs_black;
        exitButton.setImageResource(back);
        int switchesMode1 = nightModeEnabled ? R.drawable.thumb_notifs_dark : R.drawable.thumb_notifs_light;

        if (nightModeEnabled) {
            constraintLayout.setBackgroundColor(Color.BLACK);
            notificationsText.setTextColor(Color.WHITE);
            switcherVibrate.setTextColor(Color.WHITE);
            switcherTime.setTextColor(Color.WHITE);
            switcherTask.setTextColor(Color.WHITE);
            switcherRequest.setTextColor(Color.WHITE);
            switcherGroup.setTextColor(Color.WHITE);
            textSound.setTextColor(Color.WHITE);

        } else {
            constraintLayout.setBackgroundColor(Color.WHITE);
            notificationsText.setTextColor(Color.BLACK);
            switcherVibrate.setTextColor(Color.BLACK);
            switcherTime.setTextColor(Color.BLACK);
            switcherTask.setTextColor(Color.BLACK);
            switcherRequest.setTextColor(Color.BLACK);
            switcherGroup.setTextColor(Color.BLACK);
            textSound.setTextColor(Color.BLACK);
        }
        Switch switchElement1 = findViewById(R.id.vibrateSwitch);
        Switch switchElement2 = findViewById(R.id.timeSwitch);
        Switch switchElement3 = findViewById(R.id.taskSwitch);
        Switch switchElement4 = findViewById(R.id.requestSwitch);
        Switch switchElement5 = findViewById(R.id.groupSwitch);
        switchElement1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(1000);
                    }
                }
            }
        });
        switchElement2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(1000);
                    }
                }
            }
        });
        switchElement3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(1000);
                    }
                }
            }
        });
        switchElement4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(1000);
                    }
                }
            }
        });
        switchElement5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(1000);
                    }
                }
            }
        });
    }
    public void onSettingsPage(View view) {
        Intent intent = new Intent(this, SettingsPage.class);
        SettingsPage.aSwitch = view.findViewById(R.id.switchButton);
        //intent.putExtra("nightModeEnabled", aSwitch.isChecked());
        view.getContext().startActivity(intent);
        finish();
    }
}