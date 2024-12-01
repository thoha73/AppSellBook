package com.example.appsellbook.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.DTOs.CartDetail;
import com.example.appsellbook.DTOs.Image;
import com.example.appsellbook.R;
import com.example.appsellbook.Utils.CurrencyFormat;
import com.example.appsellbook.activities.Cart;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.example.appsellbook.interfaces.OnCheckBoxChangeListener;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private OnCheckBoxChangeListener listener;
    private Activity context;
    private ArrayList<CartDetail> listCart;
    public CartAdapter(Activity context,ArrayList<CartDetail> listCart,OnCheckBoxChangeListener listener){
        this.context = context;
        this.listCart = listCart;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDetail cartDetail = listCart.get(position);
        holder.tv_name.setText(cartDetail.getBookName());
        holder.quantity.setText(cartDetail.getQuantity()+"");
        holder.price.setText(CurrencyFormat.formatCurrency(cartDetail.getSellPrice()));
        String base64Image = cartDetail.getImages().get(0).getImageData();
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);
        int cartDetailId= cartDetail.getCartDetailId();
        holder.checkbox.setChecked(cartDetail.isSelected());
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Cập nhật giá trị isSelected trong CartDetail
            cartDetail.setSelected(isChecked);
            updateCheckBox(cartDetailId, isChecked);
            if (listener != null) {
                listener.onCheckBoxChanged();
            }
        });
        holder.btn_delete.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                removeItem(position);
                DeleteCartDetail(cartDetailId);
            }

        });
        holder.btn_decrease.setOnClickListener(v -> {
            int quantity = cartDetail.getQuantity();
            if (quantity > 1) {
                quantity--;
                cartDetail.setQuantity(quantity);
                holder.quantity.setText(String.valueOf(quantity));
                updateQuantity(cartDetailId,quantity);
            }
        });
        holder.btn_increase.setOnClickListener(v->{
            int quantity = cartDetail.getQuantity();
            quantity++;
            cartDetail.setQuantity(quantity);
            holder.quantity.setText(String.valueOf(quantity));
            updateQuantity(cartDetailId,quantity);
        });

    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }
    public void removeItem(int position) {
        listCart.remove(position);
        notifyItemRemoved(position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name;
        TextView quantity;
        TextView price;
        CheckBox checkbox;
        Button btn_delete,btn_increase,btn_decrease;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.product_quantity);
            imageView = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.product_price);
            checkbox=itemView.findViewById(R.id.checkbox);
            btn_delete=itemView.findViewById(R.id.delete_button);
            btn_increase=itemView.findViewById(R.id.increase_button);
            btn_decrease=itemView.findViewById(R.id.decrease_button);


        }
    }
    public void updateCheckBox(int cartDetailId, boolean isChecked) {
        GraphQLApiService apiService = RetrofitClient.getClient(context).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "    updateCheckBox(cartDetailId: "+cartDetailId+",isSelected: "+isChecked+")\n" +
                "  }";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    // Assuming the response contains a "success" field indicating the result of the mutation
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Boolean success = (Boolean) dataMap.get("updateCheckBox");

                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("CartAdapter", "Checkbox updated successfully for cartDetailId: " + cartDetailId);
                        } else {
                            Log.d("CartAdapter", "Checkbox update failed for cartDetailId: " + cartDetailId);
                        }
                    }
                } else {
                    Log.e("GraphQL Error", "Mutation failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error during mutation", t);
            }
        });
    }
    public void DeleteCartDetail(int cartDetailId) {
        GraphQLApiService apiService = RetrofitClient.getClient(context).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "deleteCartDetail(cartDetailId:"+ cartDetailId+")  \n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    // Assuming the response contains a "success" field indicating the result of the mutation
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Boolean success = (Boolean) dataMap.get("deleteCartDetail");

                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("CartAdapter", "Delete successfully for cartDetailId: " + cartDetailId);
                        } else {
                            Log.d("CartAdapter", "Delete failed for cartDetailId: " + cartDetailId);
                        }
                    }
                } else {
                    Log.e("GraphQL Error", "Mutation failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error during mutation", t);
            }
        });
    }
    public void updateQuantity(int cartDetailId, int quantity) {
        GraphQLApiService apiService = RetrofitClient.getClient(context).create(GraphQLApiService.class);
        String query = " mutation{\n" +
                "  updateQuantity(cartDetailId: "+cartDetailId+", quantity: "+quantity+")\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    // Assuming the response contains a "success" field indicating the result of the mutation
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Boolean success = (Boolean) dataMap.get("updateQuantity");

                        // If 'success' is true, log or handle the successful update
                        if (success != null && success) {
                            Log.d("CartAdapter", "Update successfully for quantity: " + cartDetailId);
                        } else {
                            Log.d("CartAdapter", "Update failed for quantity: " + cartDetailId);
                        }
                    }
                } else {
                    Log.e("GraphQL Error", "Mutation failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error during mutation", t);
            }
        });
    }
}