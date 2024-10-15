package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChangePassword extends AppCompatActivity {
    String role;

    BottomNavigationView bottom_NavigationView;
    @SuppressLint({"MissingInflatedId", "ResourceType"})
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

//
//        img_profile = findViewById(R.drawable.icon_account);
//        tv_profile = findViewById(R.id.tv_profile);
//        role = getIntent().getStringExtra("role");
//
//        if ("shop".equals(role)) {
//            img_profile.setImageResource(R.drawable.book_solid);
//            tv_profile.setText("Product");
//        } else {
//            img_profile.setImageResource(R.drawable.user_regular);
//            tv_profile.setText("Profile");
//        }

        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_profile){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });
    }

//    private void navigateToHome() {
//        if ("user".equals(role)) {
//            startActivity(new Intent(ChangePassword.this, Home.class));
//        } else {
//            startActivity(new Intent(ChangePassword.this, Home1.class));
//        }
//    }
//
//    private void navigateToNotification() {
//        if ("user".equals(role)) {
//            startActivity(new Intent(ChangePassword.this, Notification.class));
//        } else {
//            startActivity(new Intent(ChangePassword.this, OwnerNotification.class));
//        }
//    }
//
//    private void navigateToSearch() {
//        if ("user".equals(role)) {
//            startActivity(new Intent(ChangePassword.this, Home.class));
//        } else {
//            startActivity(new Intent(ChangePassword.this, Home1.class));
//        }
//    }
//
//    private void navigateToSettings() {
//        if ("user".equals(role)) {
//            startActivity(new Intent(ChangePassword.this, Settings.class));
//        } else {
//            startActivity(new Intent(ChangePassword.this, ShopOwner.class));
//        }
//    }
//
//    private void navigateToProfile() {
//        if ("user".equals(role)) {
//            startActivity(new Intent(ChangePassword.this, Profile.class));
//        } else {
//            startActivity(new Intent(ChangePassword.this, NewProduct.class));
//        }
//    }
}
