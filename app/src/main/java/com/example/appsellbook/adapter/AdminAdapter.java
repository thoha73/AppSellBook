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

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.adminViewHolder>{
    Context myContext;
    List<Statistic> listStatistic;
    public AdminAdapter(Context myContext) {
        this.myContext = myContext;
    }
    public void setData(List<Statistic> list){
        this.listStatistic=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public adminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_admin,parent,false);
        return new adminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminViewHolder holder, int position) {
        Statistic statistic=listStatistic.get(position);
        if(statistic==null){
            return;
        }
        holder.textViewTitle.setText(statistic.getTitle());
        holder.textViewNumber.setText(statistic.getValue());
        if (statistic.getTitle().equals("Total user")) {
            holder.viewCircle.setBackgroundResource(R.drawable.circle_background); // Example color
        } else if (statistic.getTitle().equals("Product")) {
            holder.viewCircle.setBackgroundResource(R.drawable.circle_background_small); // Example color
        } else {
            holder.viewCircle.setBackgroundResource(R.drawable.background_circle_orders); // Example color
        }
    }

    @Override
    public int getItemCount() {
        if(listStatistic!=null){
            return listStatistic.size();
        }
        return 0;
    }

    public class adminViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private View viewCircle;
        private TextView textViewNumber;

        public adminViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            viewCircle = itemView.findViewById(R.id.circle);
            textViewNumber = itemView.findViewById(R.id.texView_number);
        }
    }
}
