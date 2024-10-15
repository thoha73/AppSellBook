package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Book;
import com.example.appsellbook.model.User;
import com.example.appsellbook.adapter.NotificationShopAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OwnerNotification extends AppCompatActivity {
    ImageView img_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownernotification);
        // Tạo danh sách người dùng
        List<User> name = new ArrayList<>(Arrays.asList(
                new User("Võ Vĩ Khương", "vikhuong92", "123"),
                new User("Nguyễn Thọ Hà", "thoha73", "123"),
                new User("Phan Anh Duy", "pad76", "123"),
                new User("Trần Thanh Vỹ", "thanhvy92", "123"),
                new User("Quang Thảo", "quangthao75", "123")
        ));

        // Tạo danh sách hình ảnh (Book chứa id của hình ảnh)
        Book[] image = new Book[]{
                new Book(R.drawable.imguser),
                new Book(R.drawable.user1),
                new Book(R.drawable.user2),
                new Book(R.drawable.user3),
                new Book(R.drawable.user4)
        };

        // Khởi tạo ListView và CustomAdapter
        ListView listViewNotification = findViewById(R.id.lv1);
        NotificationShopAdapter adapter = new NotificationShopAdapter(OwnerNotification.this, R.layout.layout_ownernotification, name, image);

        // Thiết lập adapter cho ListView
        listViewNotification.setAdapter(adapter);
        img_back = findViewById(R.id.imgView_Notificationback);
        img_back.setOnClickListener(v->finish());


        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), ShopOwner.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_product){
                    startActivity(new Intent(getApplicationContext(), PostNewProduct.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });
    }
}
