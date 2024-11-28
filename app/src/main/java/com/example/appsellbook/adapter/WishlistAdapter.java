package com.example.appsellbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.R;

import java.util.ArrayList;

public class WishlistAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    public WishlistAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        this.books = books;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.wishlist_item, parent, false);
        }

        // Lấy dữ liệu của sách tại vị trí `position`
        Book currentBook = books.get(position);

        // Ánh xạ các view
        ImageView imgBook = convertView.findViewById(R.id.imageViewBook);
        TextView tvBookName = convertView.findViewById(R.id.textViewBookName);
        TextView tvAuthor = convertView.findViewById(R.id.textViewAuthor);

        if (currentBook.getImages() != null && !currentBook.getImages().isEmpty()) {
            String imageData = currentBook.getImages().get(0).getImageData();
            Log.d("ImageData", "Image Data: " + imageData);
            Bitmap bitmap = decodeBase64ToBitmap(imageData);
            if (bitmap != null) {
                imgBook.setImageBitmap(bitmap);
            } else {
                imgBook.setImageResource(R.drawable.book4);
            }
        }
        // Gán dữ liệu cho các view
//        imgBook.setImageResource(currentBook.);
        tvBookName.setText(currentBook.getBookName());
        tvAuthor.setText(currentBook.getAuthor().getAuthorName());

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
