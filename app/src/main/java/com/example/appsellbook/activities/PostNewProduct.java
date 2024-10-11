package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PostNewProduct extends AppCompatActivity {
    int SELECT_PHOTO = 1 ;
    ImageView img,img_home;
    Button btn_selectedimage,btn_POST;
    Uri uri;
    EditText edtbookName;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postproduct);
        btn_selectedimage = findViewById(R.id.btn_SelectedImage);
        img = findViewById(R.id.imgBookSelected);
        btn_POST = findViewById(R.id.btn_Post);
        edtbookName = findViewById(R.id.editTextbookname);
        img_home = findViewById(R.id.img_home);
        btn_selectedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_PHOTO);
                
            }
        });
        btn_POST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = edtbookName.getText().toString();
                if (uri != null && !bookName.isEmpty()) {
                    try {
                        // Tạo intent để chuyển dữ liệu
                        Intent intent = new Intent(PostNewProduct.this, NewProduct.class);
                        intent.putExtra("bookName", bookName);
                        intent.putExtra("bookImageUri", uri.toString()); // Chuyển URI thành String
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Hiển thị thông báo nếu không có tên sách hoặc ảnh
                    Toast.makeText(PostNewProduct.this, "Vui lòng chọn ảnh và nhập tên sách!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img_home.setOnClickListener(View->{
            Intent intent = new Intent(PostNewProduct.this,Home.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
