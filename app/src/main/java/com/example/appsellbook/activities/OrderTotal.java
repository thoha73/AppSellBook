package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;

public class OrderTotal extends AppCompatActivity {
    int image[]={R.drawable.book1,R.drawable.chitietsp,R.drawable.book3,R.drawable.book9};
    String name[] = {"Sách động lực","Sách 3 ","Chữa Lành Trái Tim","Đắc Nhân Tâm"};
    LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_total);
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        llHome.setOnClickListener(view -> {

        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(OrderTotal.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(OrderTotal.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Setting.class));
        });
        llProfile.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Profile.class));
        });
    }
}
