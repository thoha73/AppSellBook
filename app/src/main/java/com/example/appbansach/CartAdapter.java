package com.example.appbansach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Cart> {
    private Context context;
    private List<Cart> cartList;
    private int idlayout;  // Add idlayout as a member variable


    public CartAdapter(Context context, int idlayout, List<Cart> cartList) {
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


        Cart cart = getItem(position);

        if (cart != null) {
            holder.image.setImageResource(cart.getImg());
            holder.name.setText(cart.getName());
            holder.price.setText(cart.getPrice());
        }

        return convertView;
    }
}
