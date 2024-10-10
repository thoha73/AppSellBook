package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;

public class ShopOwner extends AppCompatActivity {
    LinearLayout ln1,ln2,ln3,ln4,ln5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopowner);

        ln1 = findViewById(R.id.linearLayoutN);
        ln2 = findViewById(R.id.linearLayoutOrder);
        ln3 = findViewById(R.id.linearLayoutPostP);
        ln4 = findViewById(R.id.linearLayoutChangepass);
        ln5 = findViewById(R.id.linearLayoutOut);


        ln1.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, OwnerNotification.class);
            startActivity(intent);
        });
//        ln2.setOnClickListener(v->{
//            Intent intent = new Intent(ShopOwner.this, OwnerNotification.class);
//            startActivity(intent);
//        });
//        ln3.setOnClickListener(v->{
//            Intent intent = new Intent(ShopOwner.this, OwnerNotification.class);
//            startActivity(intent);
//        });
//        ln4.setOnClickListener(v->{
//            Intent intent = new Intent(ShopOwner.this, OwnerNotification.class);
//            startActivity(intent);
//        });
        ln5.setOnClickListener(v->{
            Intent intent = new Intent(ShopOwner.this, Login.class);
            startActivity(intent);
        });
    }


}
