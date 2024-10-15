package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnLogout=findViewById(R.id.btn_logout);
        Button btnNotification=findViewById(R.id.btn_notification);
        Button btnMyCart=findViewById(R.id.btn_mycart);
        Button btnMyHictory=findViewById(R.id.btn_myhictory);
        Button btnChangePassword=findViewById(R.id.btn_changepassword);
        Button btnEdit=findViewById(R.id.btn_edit);
        btnLogout.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Login.class));
        });
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        llHome=findViewById(R.id.ll_home1);
        llNotification=findViewById(R.id.ll_notification1);
        llSearch=findViewById(R.id.ll_search1);
        llSetting=findViewById(R.id.ll_settings1);
        llProfile=findViewById(R.id.ll_profile1);
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Home.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Setting.class));
        });
        llProfile.setOnClickListener(v->{
            startActivity(new Intent(Settings.this,Profile.class));
        });

        btnNotification.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Notification.class));
        });
        btnLogout.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Login.class));
        });
        btnEdit.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,EditProfile.class));
        });
        btnChangePassword.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,ChangePassword.class));
        });
        btnMyHictory.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,History.class));
        });
        btnMyCart.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Cart.class));
        });
        }
    }