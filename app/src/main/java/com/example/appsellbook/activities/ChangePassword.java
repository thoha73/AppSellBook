package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class ChangePassword extends AppCompatActivity {
    String role;


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
        LinearLayout llHome, llNotification, llSetting, llSearch, llProfile;
        ImageView img_profile;
        TextView tv_profile;
        llHome = findViewById(R.id.ll_home);
        llNotification = findViewById(R.id.ll_notification);
        llSearch = findViewById(R.id.ll_search);
        llSetting = findViewById(R.id.ll_settings);
        llProfile = findViewById(R.id.ll_profile);
        img_profile = findViewById(R.id.img_profile);
        tv_profile = findViewById(R.id.tv_profile);
        role = getIntent().getStringExtra("role");

        if ("shop".equals(role)) {
            img_profile.setImageResource(R.drawable.book_solid);
            tv_profile.setText("Product");
        } else {
            img_profile.setImageResource(R.drawable.user_regular);
            tv_profile.setText("Profile");
        }
        llHome.setOnClickListener(view -> navigateToHome());
        llNotification.setOnClickListener(view -> navigateToNotification());
        llSearch.setOnClickListener(view -> navigateToSearch());
        llSetting.setOnClickListener(view -> navigateToSettings());
        llProfile.setOnClickListener(view -> navigateToProfile());
    }

    private void navigateToHome() {
        if ("user".equals(role)) {
            startActivity(new Intent(ChangePassword.this, Home.class));
        } else {
            startActivity(new Intent(ChangePassword.this, Home1.class));
        }
    }

    private void navigateToNotification() {
        if ("user".equals(role)) {
            startActivity(new Intent(ChangePassword.this, Notification.class));
        } else {
            startActivity(new Intent(ChangePassword.this, OwnerNotification.class));
        }
    }

    private void navigateToSearch() {
        if ("user".equals(role)) {
            startActivity(new Intent(ChangePassword.this, Home.class));
        } else {
            startActivity(new Intent(ChangePassword.this, Home1.class));
        }
    }

    private void navigateToSettings() {
        if ("user".equals(role)) {
            startActivity(new Intent(ChangePassword.this, Settings.class));
        } else {
            startActivity(new Intent(ChangePassword.this, ShopOwner.class));
        }
    }

    private void navigateToProfile() {
        if ("user".equals(role)) {
            startActivity(new Intent(ChangePassword.this, Profile.class));
        } else {
            startActivity(new Intent(ChangePassword.this, NewProduct.class));
        }
    }
}
