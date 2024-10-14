package com.example.appsellbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsellbook.R;
import com.example.appsellbook.model.Carts;

import java.util.List;

public class CartsAdapter extends ArrayAdapter<Carts> {
    private Context context;
    private List<Carts> cartList;
    private int idlayout;  // Add idlayout as a member variable


    public CartsAdapter(Context context, int idlayout, List<Carts> cartList) {
        super(context, idlayout, cartList);
        this.context = context;
        this.idlayout = idlayout;  // Assign the layout id
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(idlayout, parent, false);


            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.name = convertView.findViewById(R.id.product_name);
            holder.price = convertView.findViewById(R.id.product_price);


            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        Carts carts = getItem(position);

        if (carts != null) {
            holder.image.setImageResource(carts.getImg());
            holder.name.setText(carts.getName());
            holder.price.setText(carts.getPrice());
        }

        return convertView;
    }
}
