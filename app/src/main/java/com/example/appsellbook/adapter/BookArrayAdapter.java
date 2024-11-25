package com.example.appsellbook.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.R;
import com.example.appsellbook.activities.ProductDetail;

import java.util.ArrayList;

public class BookArrayAdapter extends ArrayAdapter<Book> {
    Activity context;
    int idLayout;
    ArrayList<com.example.appsellbook.DTOs.Book> listBook;
    public BookArrayAdapter(Activity context, int idLayout, ArrayList<com.example.appsellbook.DTOs.Book> listBook) {
        super(context, idLayout, listBook);
        this.context = context;
        this.idLayout = idLayout;
        this.listBook = listBook;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myflater= context.getLayoutInflater();
        convertView = myflater.inflate(idLayout, null);
//        int width=parent.getWidth()/3;
//        convertView.setLayoutParams(new GridView.LayoutParams(width,GridView.AUTO_FIT));
        com.example.appsellbook.DTOs.Book book = listBook.get(position);
        ImageView imageView=convertView.findViewById(R.id.imageView);
        if (book.getImages() != null && !book.getImages().isEmpty()) {
            String imageData = book.getImages().get(0).getImageData();
            Log.d("ImageData", "Image Data: " + imageData);
            Bitmap bitmap = decodeBase64ToBitmap(imageData);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.book4);
            }
        }
        TextView textView=convertView.findViewById(R.id.textView);
        textView.setText(book.getBookName());
//        convertView.setOnClickListener(v -> {
//            // Chuyển đến ProductDetailActivity và truyền dữ liệu về cuốn sách
//            Intent intent = new Intent(context, ProductDetail.class);
//            intent.putExtra("bookId", book.getBookId()); // Truyền ID của cuốn sách
//            context.startActivity(intent);
//        });
        return convertView;
    }
    private Bitmap decodeBase64ToBitmap(String base64String) {
        try {
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (IllegalArgumentException e) {
            Log.e("Base64", "Invalid Base64 string", e);
            return null;
        }
    }
}
