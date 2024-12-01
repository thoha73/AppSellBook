package com.example.appsellbook.adapter;

import android.app.Activity;
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

import java.util.ArrayList;

public class OrderTotalAdapter extends RecyclerView.Adapter<OrderTotalAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<CartDetail> selectedItems;
    public OrderTotalAdapter(Activity context,ArrayList<CartDetail> selectedItems){
        this.context =  context ;
        this.selectedItems = selectedItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_layout_ordertotal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        selectedItems = DataCache.getSelectedItems();
        holder.tv_name.setText(selectedItems.get(position).getBookName());
        holder.quantity.setText("X"+selectedItems.get(position).getQuantity());
        holder.price.setText(selectedItems.get(position).getSellPrice()+"đ");
        String base64Image = selectedItems.get(position).getImages().get(0).getImageData();
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name;
        TextView quantity;
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.product_quantity);
            imageView = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.product_price);



        }
    }
}
