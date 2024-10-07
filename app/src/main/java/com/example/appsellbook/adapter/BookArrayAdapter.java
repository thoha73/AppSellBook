package com.example.appsellbook.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;

public class BookArrayAdapter extends ArrayAdapter<Book> {
    Activity context;
    int idLayout;
    ArrayList<Book> listBook;
    public BookArrayAdapter(Activity context, int idLayout, ArrayList<Book> listBook) {
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
        Book book = listBook.get(position);
        ImageView imageView=convertView.findViewById(R.id.imageView);
        imageView.setImageResource(book.getImage());
        TextView textView=convertView.findViewById(R.id.textView);
        textView.setText(book.getBookName());
        return convertView;
    }
}
