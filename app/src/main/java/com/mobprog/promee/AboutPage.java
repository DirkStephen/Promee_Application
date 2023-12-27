package com.mobprog.promee;

//import static com.mobprog.promee.aSwitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutPage extends AppCompatActivity {

    private TextView about1;
    private TextView about2;
    private TextView about3;
    private TextView about4;
    private TextView about5;
    private TextView about6;
    private TextView about7;

    private ImageView imgBack;

    private ConstraintLayout contentAbout;

    private ImageButton imgExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean nightModeEnabled = sharedPreferences.getBoolean("nightModeEnabled", false);

        about1 = findViewById(R.id.aboutTextView);
        about2 = findViewById(R.id.aboutPromeeText);
        about3 = findViewById(R.id.welcomeTextView);
        about4 = findViewById(R.id.meetText);
        about5 = findViewById(R.id.ourText);
        about6 = findViewById(R.id.peopleText);
        about7 = findViewById(R.id.namesText);
        contentAbout = findViewById(R.id.aboutContent);
        imgExitButton = findViewById(R.id.exit_button);

        int imageResource1 = nightModeEnabled ? R.drawable.back_button_white/*ON*/ : R.drawable.back_button;
        imgExitButton.setImageResource(imageResource1);
        int backgroundColor = nightModeEnabled ? Color.BLACK : Color.parseColor("#FFA500");
        about1.setBackgroundColor(backgroundColor);

        if (nightModeEnabled){
            contentAbout.setBackgroundColor(Color.BLACK);
            about1.setTextColor(Color.WHITE);
            about2.setTextColor(Color.WHITE);
            about3.setTextColor(Color.WHITE);
            about4.setTextColor(Color.WHITE);
            about5.setTextColor(Color.WHITE);
            about6.setTextColor(Color.WHITE);
            about7.setTextColor(Color.WHITE);
        }else {
            contentAbout.setBackgroundColor(Color.WHITE);
            about1.setTextColor(Color.BLACK);
            about2.setTextColor(Color.BLACK);
            about3.setTextColor(Color.BLACK);
            about4.setTextColor(Color.BLACK);
            about5.setTextColor(Color.BLACK);
            about6.setTextColor(Color.BLACK);
            about7.setTextColor(Color.BLACK);
        }
    }
    public void onSettingsPage(View view) {

        Intent intent = new Intent(this, SettingsPage.class);

        SettingsPage.aSwitch = view.findViewById(R.id.switchButton);
        //intent.putExtra("nightModeEnabled", aSwitch.isChecked());
        view.getContext().startActivity(intent);

        finish();
    }
}