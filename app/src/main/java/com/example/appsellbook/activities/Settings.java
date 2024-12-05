package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Set;

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
        TextView tv_username = findViewById(R.id.username);
        TextView tv_userrole = findViewById(R.id.user_role);
        TextView tv_point = findViewById(R.id.tv_point);
        SessionManager sessionManager = new SessionManager(this);
        String username = sessionManager.getUsername();
        int point = sessionManager.getPoint();
        Set<Integer> list = sessionManager.getRoleIds();
        int role = 0;
        for (int x : list){
            role = x ;
        }
        tv_username.setText(username);
        tv_point.setText(point+"");
        if (role == 1){
            tv_userrole.setText("User");
        }else {
            tv_userrole.setText("Shop");
        }
//        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
//        int userPoint = sharedPreferences.getInt("userPoint", 0);
//        tv_point.setText(String.valueOf(userPoint));
        Button btnLogout=findViewById(R.id.btn_logout);
        Button btnNotification=findViewById(R.id.btn_notification);
        Button btnMyCart=findViewById(R.id.btn_mycart);
        Button btnMyHictory=findViewById(R.id.btn_myhictory);
        Button btnChangePassword=findViewById(R.id.btn_changepassword);
        Button btnEdit=findViewById(R.id.btn_edit);
        Button btnWishList = findViewById(R.id.btn_wishlist);

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
        btnWishList.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this, WishList.class));
        });
        }

    }