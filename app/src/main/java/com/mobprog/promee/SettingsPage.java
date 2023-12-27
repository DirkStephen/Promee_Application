package com.mobprog.promee;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsPage extends AppCompatActivity {

    static boolean nightModeEnabled = false;
    private ConstraintLayout constraintLayout;
    static Switch aSwitch;
    private Switch switcher;
    private TextView notifications;
    private TextView about;
    private TextView settingsTextView;
    private ImageButton exitButton;
    private ImageButton notifNextButton;
    private ImageButton aboutNextButton;
    private ImageView notifButton;
    private ImageView aboutButton;
    private ImageView darkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        constraintLayout = findViewById(R.id.content);
        switcher = findViewById(R.id.switchButton);
        notifications = findViewById(R.id.notifications);
        about = findViewById(R.id.about);
        settingsTextView = findViewById(R.id.aboutTextView);
        exitButton= findViewById(R.id.exit_button);
        notifButton= findViewById(R.id.imgNotif);
        aboutButton= findViewById(R.id.imgAbout);
        notifNextButton = findViewById(R.id.notificationsNext);
        aboutNextButton = findViewById(R.id.aboutNext);
        darkButton = findViewById(R.id.darkImg);

        switcher.setChecked(nightModeEnabled);

        updateTextBasedOnSwitchState();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        nightModeEnabled = sharedPreferences.getBoolean("nightModeEnabled", false);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("nightModeEnabled", isChecked);
                editor.apply();

                int backgroundColor = isChecked ? Color.BLACK : Color.parseColor("#FFA500");
                settingsTextView.setBackgroundColor(backgroundColor);

                int imageResource1 = isChecked ? R.drawable.back_button_white/*ON*/ : R.drawable.back_button;
                exitButton.setImageResource(imageResource1);

                int imageResource2 = isChecked ? R.drawable.baseline_notifications_white : R.drawable.baseline_notifications_24;
                notifButton.setImageResource(imageResource2);

                int imageResource3 = isChecked ? R.drawable.round_info_white : R.drawable.round_info_24;
                aboutButton.setImageResource(imageResource3);

                int imageResource4 = isChecked ? R.drawable.nexticon_white : R.drawable.nexticon_black;
                notifNextButton.setImageResource(imageResource4);

                int imageResource5 = isChecked ? R.drawable.aboutnext_white : R.drawable.aboutnext_black;
                aboutNextButton.setImageResource(imageResource5);

                int imageResource6 = isChecked ? R.drawable.baseline_dark_mode_24 : R.drawable.baseline_dark_mode_black;
                darkButton.setImageResource(imageResource6);

                if (isChecked) {

                    constraintLayout.setBackgroundColor(Color.BLACK);
                    notifications.setTextColor(Color.WHITE);
                    about.setTextColor(Color.WHITE);
                    switcher.setTextColor(Color.WHITE);
                    switcher.setText("Night Mode");
                    settingsTextView.setTextColor(Color.WHITE);
                    settingsTextView.setBackgroundColor(Color.BLACK);
                } else {

                    constraintLayout.setBackgroundColor(Color.WHITE);
                    notifications.setTextColor(Color.BLACK);
                    about.setTextColor(Color.BLACK);
                    switcher.setTextColor(Color.BLACK);
                    switcher.setText("Light Mode");
                    settingsTextView.setTextColor(Color.BLACK);
                }
            }
        });
    }

    private void updateTextBasedOnSwitchState() {
        if (switcher.isChecked()) {
            switcher.setText("Night Mode");
        } else {
            switcher.setText("Light Mode");
        }
    }
    public void onNotificationButtonClick(View view) {

        Intent intent = new Intent(SettingsPage.this, NotificationsPage.class);
        startActivity(intent);
    }

    public void onAboutButtonClick(View view) {

        Intent intent = new Intent(this, AboutPage.class);

        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
