package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.model.Orders;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;

public class ShopOwner extends AppCompatActivity {
    LinearLayout ln1,ln2,ln3,ln4,ln5;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopowner);
        TextView name = findViewById(R.id.textNameowner);
        TextView role  = findViewById(R.id.role);
        SessionManager sessionManager = new SessionManager(this);
        String nameshop = sessionManager.getUsername();
        Set<Integer> list = sessionManager.getRoleIds();
        int roles = 0;
        for (int x : list){
            roles = x ;
        }
        name.setText(nameshop);
        if (roles == 2){
            role.setText("Shop");
        }else {
            role.setText("User");
        }
        ln1 = findViewById(R.id.linearLayoutN);
        ln2 = findViewById(R.id.linearLayoutOrder);
        ln3 = findViewById(R.id.linearLayoutPostP);
        ln4 = findViewById(R.id.linearLayoutChangepass);
        ln5 = findViewById(R.id.linearLayoutOut);

        ln1.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, OwnerNotification.class);
            startActivity(intent);
        });
        ln2.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, OrdersActivity.class);
            startActivity(intent);
        });
        ln3.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, PostNewProduct.class);
            startActivity(intent);
        });
        ln4.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, ChangePassword2.class);
//            intent.putExtra("role","shop");
            startActivity(intent);
        });
        ln5.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, Login.class);
            startActivity(intent);
        });

        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setSelectedItemId(R.id.menu_setting);
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
                    startActivity(new Intent(getApplicationContext(), OwnerNotification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
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
