package com.example.appsellbook.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<com.example.appsellbook.DTOs.Book> books;
    private boolean isNewProduct;
    public BookAdapter(List<Book> books, boolean isNewProduct) {
        this.books = books;
        this.isNewProduct = isNewProduct;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_carditem_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book;
        if (isNewProduct) {
            book = books.get(position);

        } else {
            book = books.get(books.size() - 5 + position);
        }
        holder.textView.setText(book.getBookName());
        String base64Image = book.getImages().get(0).getImageData();
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book);
            textView = itemView.findViewById(R.id.tv_bookName);
        }
    }
}