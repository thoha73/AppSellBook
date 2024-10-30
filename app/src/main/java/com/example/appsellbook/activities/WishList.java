package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.WishlistAdapter;
import com.example.appsellbook.model.Book;
import com.example.appsellbook.model.Notification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class WishList extends AppCompatActivity {
    private ListView listViewWishlist;
    private ArrayList<Book> wishlistItems;
    private WishlistAdapter wishlistAdapter;
    private ImageView img_back;
    private BottomNavigationView bottom_NavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        listViewWishlist = findViewById(R.id.listView_WishList);
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        wishlistItems = new ArrayList<>();

        Intent intent = getIntent();
        String anh = intent.getStringExtra("anh");
        int image = intent.getIntExtra("image", 0);

        int image1 = intent.getIntExtra("image1", 0);
        String name = intent.getStringExtra("name3");
        String author = intent.getStringExtra("author3");
        if (name != null && author != null) {
                Book book = new Book(image, name, author);
                wishlistItems.add(book);


        }
        wishlistAdapter = new WishlistAdapter(this, wishlistItems);
        listViewWishlist.setAdapter(wishlistAdapter);

        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

}


