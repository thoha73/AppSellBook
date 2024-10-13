package com.example.appbansach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Orders> {

    private List<Orders> ordersList;
    private int resourceLayout;

    public OrdersAdapter(@NonNull Context context, int resourceLayout, @NonNull List<Orders> ordersList) {
        super(context, resourceLayout, ordersList);
        this.resourceLayout = resourceLayout;
        this.ordersList = ordersList;
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Nullable
    @Override
    public Orders getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceLayout, parent, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.icon);
            holder.order = convertView.findViewById(R.id.orders);
            holder.content = convertView.findViewById(R.id.content);
            convertView.setTag(holder); // Lưu lại ViewHolder để tái sử dụng
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Orders orders = getItem(position);

        if (orders != null) {
            holder.img.setImageResource(orders.getImg());
            holder.order.setText(orders.getOrderer());
            holder.content.setText(orders.getContent());
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView img;
        TextView order;
        TextView content;
    }
}
