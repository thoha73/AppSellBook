package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Review extends AppCompatActivity {
    private ImageView imageView1,imageView2,imageView3;
    private Button btn_download;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        int[] imageIds = getIntent().getIntArrayExtra("imageIds");

        if (imageIds != null && imageIds.length >= 3) {
            imageView1 = findViewById(R.id.page1);
            imageView2 = findViewById(R.id.page2);
            imageView3 = findViewById(R.id.page3);

            imageView1.setImageResource(imageIds[0]);
            imageView2.setImageResource(imageIds[1]);
            imageView3.setImageResource(imageIds[2]);
        }
        btn_download = findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImages();
            }
        });

        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());
        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

    private void downloadImages() {
        saveImageToGallery(imageView1, "page1");
        saveImageToGallery(imageView2, "page2");
        saveImageToGallery(imageView3, "page3");
        Toast.makeText(this, "Hình ảnh đã được tải xuống", Toast.LENGTH_SHORT).show();
    }

    private void saveImageToGallery(ImageView imageView, String imageName) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Sử dụng MediaStore để lưu ảnh trên Android 10 trở lên
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName + ".jpg");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                // Lưu ảnh vào MediaStore
                fos = getContentResolver().openOutputStream(
                        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values));
            } else {
                // Lưu ảnh vào thư mục Pictures cho các phiên bản Android thấp hơn
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File file = new File(path, imageName + ".jpg");
                fos = new FileOutputStream(file);
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if (fos != null) {
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi lưu hình ảnh", Toast.LENGTH_SHORT).show();
        }
    }
}

