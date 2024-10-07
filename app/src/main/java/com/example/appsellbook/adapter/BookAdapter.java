package com.example.appsellbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private Context myContext;
    private List<Book> listBook;
    public BookAdapter(Context myContext) {
        this.myContext = myContext;
    }
    public void setData(List<Book> list){
        this.listBook=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_carditem_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book=listBook.get(position);
        if(book==null){
            return;
        }
        holder.imageView.setImageResource(book.getImage());
        holder.textViewName.setText(book.getBookName());
    }

    @Override
    public int getItemCount() {
        if(listBook!=null){
            return listBook.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book);
            textViewName = itemView.findViewById(R.id.tv_bookName);
        }
    }
}
