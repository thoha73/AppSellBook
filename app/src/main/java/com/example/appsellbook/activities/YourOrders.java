package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.CartDetail;
import com.example.appsellbook.DTOs.DataCache;
import com.example.appsellbook.R;
import com.example.appsellbook.adapter.OrderTotalAdapter;
import com.example.appsellbook.adapter.YourOrderAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class YourOrders extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tv_detail_your_order,tv_his;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.rcv_your_order);
        tv_his = findViewById(R.id.tv_history);
        tv_his.setOnClickListener(view -> {
            startActivity(new Intent(YourOrders.this, History.class));
        });
        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        recyclerView.setLayoutManager(new LinearLayoutManager(YourOrders.this));
        YourOrderAdapter adapter = new YourOrderAdapter(this,this::onItemClick);
        recyclerView.setAdapter(adapter);


        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setSelectedItemId(R.id.menu_profile);
        bottom_NavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_notification) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_search) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_setting) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(0, 0);
                return true;
            }
            if (id == R.id.menu_profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ChangeSuccess.class);
        startActivity(intent);
    }
}