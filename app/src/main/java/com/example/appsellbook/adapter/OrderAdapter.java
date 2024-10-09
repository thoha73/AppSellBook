package com.example.appsellbook.adapter;

import android.content.Context;
import android.content.Intent;
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


import com.example.appsellbook.R;
import com.example.appsellbook.activities.Feedback;
import com.example.appsellbook.model.Order;
import com.example.appsellbook.model.OrderDetails;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends ArrayAdapter<Order> {
    private int resource;
    private Context myContext;
    private List<Order> orderList;
    public OrderAdapter(Context myContext,int resource, ArrayList<Order> orderList) {
        super(myContext,resource,orderList);
        this.myContext=myContext;
        this.orderList=orderList;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(myContext).inflate(R.layout.layout_item_history,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageV_book=convertView.findViewById(R.id.imageV_book);
            viewHolder.tv_bookName=convertView.findViewById(R.id.tv_bookName);
            viewHolder.tv_quantity=convertView.findViewById(R.id.tv_quantity);
            viewHolder.tv_totalPay=convertView.findViewById(R.id.tv_totalPay);
            viewHolder.btn_fback=convertView.findViewById(R.id.btn_feedback);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        Order order = orderList.get(position);
        if (order != null && order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
            OrderDetails details = order.getOrderDetails().get(0); // Lấy OrderDetails đầu tiên
            if (details.getBook() != null) {
                viewHolder.imageV_book.setImageResource(details.getBook().getImage());
                viewHolder.tv_bookName.setText(details.getBook().getBookName());
            }
            viewHolder.tv_quantity.setText(String.valueOf(details.getQuantity()));
            double totalPrice = details.getPrice() * details.getQuantity();
            String formatterPrice = formatCurrency(totalPrice);
            viewHolder.tv_totalPay.setText(formatterPrice + " đ");
//            viewHolder.btn_fback.setOnClickListener(view -> {
//                Intent intent = new Intent(myContext, Feedback.class);
//                intent.putExtra("bookName", details.getBook().getBookName());
//                intent.putExtra("image", details.getBook().getImage());
//                intent.putExtra("bookId", details.getBook().getBookId());
//                myContext.startActivity(intent);
//            });
            viewHolder.btn_fback.setOnClickListener(view -> {
                if (details.getBook() != null) {
                    Intent intent = new Intent(myContext, Feedback.class);
                    intent.putExtra("bookName", details.getBook().getBookName());
                    intent.putExtra("image", details.getBook().getImage());
                    myContext.startActivity(intent);
                } else {
                    // Có thể thêm một log hoặc thông báo cho người dùng
                    Log.e("OrderAdapter", "Book details are null");
                }
            });
        }
        return convertView;
    }

    public class ViewHolder{
        ImageView imageV_book;
        TextView tv_bookName;
        TextView tv_quantity;
        TextView tv_totalPay;
        Button btn_fback;

    }
    public String formatCurrency(double number){
        String result = String.format("%,.0f", number).replace(",", ".");
        return result;
    }

}
