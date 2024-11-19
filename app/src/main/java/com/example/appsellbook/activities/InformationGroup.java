package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;

public class InformationGroup extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_group);
        ImageView ic_next;
        ic_next = findViewById(R.id.ic_next);
        ic_next.setOnClickListener(view -> {
            startActivity(new Intent(InformationGroup.this, HKVBook.class));
        });
    }
}
