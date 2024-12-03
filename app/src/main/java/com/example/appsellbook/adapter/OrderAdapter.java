package com.example.appsellbook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsellbook.DTOs.OrderDetails;

import com.example.appsellbook.R;
import com.example.appsellbook.activities.Feedback;
import com.example.appsellbook.model.Order;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Activity context;
    private  ArrayList<OrderDetails> listOrder;
    public OrderAdapter(Activity context,ArrayList<OrderDetails> listOrder){
        this.listOrder = listOrder;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetails orderDetails = listOrder.get(position);
        holder.tv_bookName.setText(orderDetails.getBookName());
        holder.tv_quantity.setText(orderDetails.getQuantity()+ "");
        holder.tv_totalPay.setText(formatCurrency(orderDetails.getSellPrice()*orderDetails.getQuantity())+"");
        String base64Image = orderDetails.getImages().get(0).getImageData();
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.image_book.setImageBitmap(decodedByte);
        holder.btn_fback.setOnClickListener(view -> {
            try {
                // Save the image to a file
                File imageFile = new File(view.getContext().getCacheDir(), "book_image.png");
                FileOutputStream fos = new FileOutputStream(imageFile);
                decodedByte.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();

                // Log the file path
                Log.d("Feedback", "Image saved at: " + imageFile.getAbsolutePath());

                // Pass book name and file path
                Intent intent = new Intent(view.getContext(), Feedback.class);
                intent.putExtra("bookName", orderDetails.getBookName());
                intent.putExtra("imagePath", imageFile.getAbsolutePath());
                intent.putExtra("bookId",orderDetails.getBookId());

                view.getContext().startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_book;
        TextView tv_bookName;
        TextView tv_quantity;
        TextView tv_totalPay;
        Button btn_fback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_book = itemView.findViewById(R.id.imageV_book);
            tv_bookName = itemView.findViewById(R.id.tv_bookName);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_totalPay = itemView.findViewById(R.id.tv_totalPay);
            btn_fback = itemView.findViewById(R.id.btn_feedback);
        }
    }
    public String formatCurrency(double number){
        String result = String.format("%,.0f", number).replace(",", ".");
        return result;
    }

}
