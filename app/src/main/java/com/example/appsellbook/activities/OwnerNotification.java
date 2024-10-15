package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Book;
import com.example.appsellbook.model.User;
import com.example.appsellbook.adapter.NotificationShopAdapter;
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
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_product);
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

        llHome.setOnClickListener(view -> {
            startActivity(new Intent(OwnerNotification.this,Home1.class));
        });
        llNotification.setOnClickListener(view -> {
//            startActivity(new Intent(OwnerNotification.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(OwnerNotification.this,Home1.class));

        });
        llSetting.setOnClickListener(view -> {
           startActivity(new Intent(OwnerNotification.this,ShopOwner.class));
        });
        llProfile.setOnClickListener(view -> {
           startActivity(new Intent(OwnerNotification.this,PostNewProduct.class));
        });
    }
}
