package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.NotificationUserAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Notification extends AppCompatActivity {
    private ListView lv_Notification;


    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lv_Notification=findViewById(R.id.listView_Notification);
        List<com.example.appsellbook.model.Notification> list= new ArrayList<>();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat df= new SimpleDateFormat("dd 'thg' MM", new Locale("vi","VN"));

        list.add(new com.example.appsellbook.model.Notification("Giảm giá đến 15% cho ngày 11/11",df.format(calendar.getTime()),false));
        list.add(new com.example.appsellbook.model.Notification("Giảm giá đến 10% cho ngày 12/11",df.format(calendar.getTime()),false));
        list.add(new com.example.appsellbook.model.Notification("Đơn hàng của bạn đã được xác nhận! Vui lòng chú ý điện thoại để nhận hàng.",df.format(calendar.getTime()),false));
        NotificationUserAdapter notificationUserAdapter= new NotificationUserAdapter(this,R.layout.layout_item_notification,list);
        lv_Notification.setAdapter(notificationUserAdapter);

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());

        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setSelectedItemId(R.id.menu_notification);
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
}