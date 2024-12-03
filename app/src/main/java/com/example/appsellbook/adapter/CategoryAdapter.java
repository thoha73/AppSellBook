package com.example.appsellbook.adapter;

import android.content.Context;
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

import com.example.appsellbook.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BookViewHolder> {
    private List<com.example.appsellbook.DTOs.Book> books;
    private Context context;

    public CategoryAdapter(Context context, List<com.example.appsellbook.DTOs.Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_carditem_book, parent, false);
        return new BookViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        com.example.appsellbook.DTOs.Book book = books.get(position);
        if (book.getImages() != null && !book.getImages().isEmpty()) {
            loadImageFromBase64(holder.imageViewBook, book.getImages().get(0).getImageData());
        }
        if (book.getCategories() != null && !book.getCategories().isEmpty()) {
            holder.textViewCategory.setText(book.getCategories().get(0).getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBook;
        TextView textViewCategory;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBook = itemView.findViewById(R.id.imageView_book);
            textViewCategory = itemView.findViewById(R.id.tv_bookName);
        }
    }

    private void loadImageFromBase64(ImageView imageView, String base64Data) {
        byte[] decodedString = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
    }
}