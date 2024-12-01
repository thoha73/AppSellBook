package com.example.appsellbook.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.appsellbook.DTOs.CartDetail;
import com.example.appsellbook.DTOs.DataCache;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.activities.YourOrders;

import java.util.ArrayList;

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.ViewHolder> {
    private Activity context;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager ;
    private ArrayList<CartDetail> selectedItems;
    public YourOrderAdapter(Activity context){
        this.context =  context ;
        this.sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        this.sessionManager = new SessionManager(this.context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_layout_yourorder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int orderId = sharedPreferences.getInt("orderId", 0);
        holder.code.setText("DHHKVBOOK00"+orderId);
        holder.content.setText("Tổng "+DataCache.getSelectedItemsCount() + " sản phẩm");
        int point = sessionManager.getPoint();
        Double tongtien = (DataCache.getTotalOrder() - point*100);
        holder.total.setText("Tổng tiền: "+tongtien+"đ");
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView code,content,total;


        public ViewHolder(View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.order_code);
            content = itemView.findViewById(R.id.tv_order_content);
            total = itemView.findViewById(R.id.tv_total);



        }
    }
}
