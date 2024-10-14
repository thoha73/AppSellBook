package com.example.appsellbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appsellbook.R;
import com.example.appsellbook.model.OrderDetail;

import java.util.List;

public class OrderDetailAdapter extends ArrayAdapter<OrderDetail> {

    private Context context;
    private List<OrderDetail> orderDetailList; // Danh sách các đơn hàng

    public OrderDetailAdapter( int idlayout, Context context, List<OrderDetail> orderDetailList) {
        super(context, idlayout,orderDetailList);
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @Override
    public int getCount() {
        return orderDetailList.size(); // Số lượng phần tử trong danh sách
    }

    @Override
    public long getItemId(int position) {
        return position; // ID của đối tượng, ở đây là vị trí của nó
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_detail_item, parent, false);
        }
        OrderDetail orderDetail = (OrderDetail) getItem(position);
        ImageView icon = convertView.findViewById(R.id.icon);
        TextView order = convertView.findViewById(R.id.detail);
        TextView ordererName = convertView.findViewById(R.id.detail_text);

        icon.setImageResource(orderDetail.getIconResourceId());
        order.setText(orderDetail.getOrder());
        ordererName.setText(orderDetail.getOrdererName());

        return convertView;
    }
}
