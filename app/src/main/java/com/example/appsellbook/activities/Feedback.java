package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        imageV_book = findViewById(R.id.image_ItemBook);
        imageV_back=findViewById(R.id.imageV_back);
        textView_bookName = findViewById(R.id.textView_bookName);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        llHome=findViewById(R.id.ll_home);
        Intent intent = getIntent();
        imageV_book.setImageResource(intent.getIntExtra("image", -1));
        textView_bookName.setText(intent.getStringExtra("bookName"));
        imageV_back.setOnClickListener(v -> finish());
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(Feedback.this,Home.class));
        });
        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(Feedback.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(Feedback.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Setting.class));
        });
        llProfile.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Profile.class));
        });
    }
}