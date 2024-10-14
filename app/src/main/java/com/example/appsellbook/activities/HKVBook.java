package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class HKVBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hkvbook);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonSignUp = findViewById(R.id.buttonsignup);
        Button buttonLogin = findViewById(R.id.buttonlogin);
        buttonSignUp.setOnClickListener(v -> {
            startActivity(new Intent(HKVBook.this, Signup.class));
        });
        buttonLogin.setOnClickListener(v -> {
            startActivity(new Intent(HKVBook.this, Login.class));
        });

    }
}