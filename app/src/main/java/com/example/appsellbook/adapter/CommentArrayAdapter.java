package com.example.appsellbook.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Commentation;
import com.example.appsellbook.R;

import java.util.ArrayList;

public class CommentArrayAdapter extends RecyclerView.Adapter<CommentArrayAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<Commentation> listComment;

    public CommentArrayAdapter(Activity context, ArrayList<Commentation> listComment) {
        this.context = context;
        this.listComment = listComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item của RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.layout_commentationbook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy bình luận tại vị trí này
        Commentation comment = listComment.get(position);
        // Gắn dữ liệu vào các view
        holder.tv_name.setText(comment.getUser().getName());
        holder.ratingBar.setRating((float) comment.getRank());
        holder.tv_comment.setText(comment.getCommentationContent());
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    // ViewHolder lưu trữ các view của một item trong RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        RatingBar ratingBar;
        TextView tv_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tv_comment = itemView.findViewById(R.id.tv_comment);
        }
    }
}
