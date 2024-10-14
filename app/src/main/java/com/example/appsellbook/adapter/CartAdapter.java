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

import com.example.appsellbook.model.Cart;

import java.util.List;

public class CartAdapter {
    private Context myContext;
    private List<Cart> listCart;
    public CartAdapter(Context myContext) {
        this.myContext = myContext;
    }
    public void setData(List<Cart> list){
        this.listCart=list;
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {

    }

    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_carditem_book,parent,false);
        return new CartAdapter.CartViewHolder(view);
    }
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart=listCart.get(position);
        if(cart==null){
            return;
        }
        holder.imageView.setImageResource(cart.getImage());
        holder.textViewName.setText(cart.getName());
        holder.textViewPrice.setText(cart.getPrice()+"");
        holder.textViewQuantity.setText(cart.getQuantity());
    }

    public int getItemCount() {
        if(listCart!=null){
            return listCart.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName,textViewPrice,textViewQuantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_cart);
            textViewName = itemView.findViewById(R.id.tv_nameBookCart);
            textViewPrice = itemView.findViewById(R.id.tv_priceCart);
            textViewQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
