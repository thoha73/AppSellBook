package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    @SuppressLint("MissinginFlatedID")
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
        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setSelectedItemId(R.id.menu_setting);
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
        btnNotification.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Notification.class));
        });
        btnLogout.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Login.class));
        });
        btnEdit.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,WishList.class));
        });
        btnChangePassword.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, ChangePassword.class);
            intent.putExtra("role", "user");
            startActivity(intent);
        });
        btnMyHictory.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,History.class));
        });
        btnMyCart.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this,Cart.class));
        });
        }
    }