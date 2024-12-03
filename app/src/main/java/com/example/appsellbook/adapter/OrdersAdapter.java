package com.example.appsellbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appsellbook.DTOs.Order;
import com.example.appsellbook.R;
import com.example.appsellbook.activities.OrderdetailsActivity;
import com.example.appsellbook.model.OrderDetail;

import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {

    private List<Order> ordersList;
    private int resourceLayout;

    public OrdersAdapter(@NonNull Context context, int resourceLayout, @NonNull List<Order> ordersList) {
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
    public Order getItem(int position) {
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
            holder.detail = convertView.findViewById(R.id.viewdetail);
            convertView.setTag(holder); // Lưu lại ViewHolder để tái sử dụng
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order orders = getItem(position);

        if (orders != null) {
            holder.order.setText("Người đặt hàng : "+orders.getUser().getFirstName());
            String content="Đã đặt đơn hàng (Tổng số sản phẩm: "+orders.getOrderDetails().stream().count()+")";
            holder.content.setText(content);
            holder.detail.setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), OrderdetailsActivity.class);
                intent.putExtra("orderId", orders.getOrderId());
                intent.putExtra("userId", orders.getUser().getUserId());
                getContext().startActivity(intent);
            });
        }

        return convertView;
    }
    static class ViewHolder {
        ImageView img;
        TextView order;
        TextView content;
        TextView detail;
    }
}