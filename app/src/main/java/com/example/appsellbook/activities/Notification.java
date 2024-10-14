package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.NotificationUserAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Notification extends AppCompatActivity {
    private ListView lv_Notification;



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
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        lv_Notification=findViewById(R.id.listView_Notification);
        List<com.example.appsellbook.model.Notification> list= new ArrayList<>();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat df= new SimpleDateFormat("dd 'thg' MM", new Locale("vi","VN"));

        list.add(new com.example.appsellbook.model.Notification("Giảm giá đến 15% cho ngày 11/11",df.format(calendar.getTime()),false));
        list.add(new com.example.appsellbook.model.Notification("Giảm giá đến 10% cho ngày 12/11",df.format(calendar.getTime()),false));
        NotificationUserAdapter notificationUserAdapter= new NotificationUserAdapter(this,R.layout.layout_item_notification,list);
        lv_Notification.setAdapter(notificationUserAdapter);
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(Notification.this,Home.class));
        });
//        llNotification.setOnClickListener(view -> {
//            startActivity(new Intent(Notification.this,Notification.class));
//        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(Notification.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
           startActivity(new Intent(Notification.this,Settings.class));
        });
        llProfile.setOnClickListener(view -> {
           startActivity(new Intent(Notification.this,Profile.class));
        });
    }
}