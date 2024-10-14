package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(ChangePassword.this,Home.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(ChangePassword.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(ChangePassword.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
            startActivity(new Intent(ChangePassword.this,Settings.class));
        });
        llProfile.setOnClickListener(view -> {
            startActivity(new Intent(ChangePassword.this,Profile.class));
        });
    }
}