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
import com.example.appsellbook.model.Statistic;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder>{
    Context myContext;
    List<Statistic> listStatistic;
    private OnItemClickListener listener;

    public AdminAdapter(Context myContext) {
        this.myContext = myContext;
    }

    public void setData(List<Statistic> list){
        this.listStatistic = list;
        notifyDataSetChanged();
    }

    // Interface để bắt sự kiện click
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Thiết lập listener cho adapter
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Statistic statistic = listStatistic.get(position);
        if(statistic == null) {
            return;
        }

        holder.textViewTitle.setText(statistic.getTitle());
        holder.textViewNumber.setText(statistic.getValue());

        // Đổi màu của viewCircle theo tiêu chí của bạn
        if (statistic.getTitle().equals("Total user")) {
            holder.viewCircle.setBackgroundResource(R.drawable.circle_background);
        } else if (statistic.getTitle().equals("Product")) {
            holder.viewCircle.setBackgroundResource(R.drawable.circle_background_small);
        } else {
            holder.viewCircle.setBackgroundResource(R.drawable.background_circle_orders);
        }

        // Gán sự kiện click cho item
        holder.itemView.setOnClickListener(v -> {
            if(listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listStatistic != null) {
            return listStatistic.size();
        }
        return 0;
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private View viewCircle;
        private TextView textViewNumber;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            viewCircle = itemView.findViewById(R.id.circle);
            textViewNumber = itemView.findViewById(R.id.texView_number);
        }
    }
    public List<Statistic> getData() {
        return listStatistic;
    }
}

