package com.example.appsellbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Book;

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

        // Gán dữ liệu cho các view
        imgBook.setImageResource(currentBook.getImage());
        tvBookName.setText(currentBook.getBookName());
        tvAuthor.setText(currentBook.getAuthor());

        return convertView;
    }
}
