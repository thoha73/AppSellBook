package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class Feedback extends AppCompatActivity {
    private ImageView imageV_book,imageV_back;
    private TextView textView_bookName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageV_book = findViewById(R.id.image_ItemBook);
        imageV_back=findViewById(R.id.imageV_back);
        textView_bookName = findViewById(R.id.textView_bookName);
        Intent intent = getIntent();
        imageV_book.setImageResource(intent.getIntExtra("image", -1));
        textView_bookName.setText(intent.getStringExtra("bookName"));
        imageV_back.setOnClickListener(v -> finish());
    }
}