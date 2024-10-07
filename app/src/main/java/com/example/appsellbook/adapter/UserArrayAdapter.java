package com.example.appsellbook.adapter;

import android.app.Activity;
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
import com.example.appsellbook.model.Account;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;
import java.util.List;

public class UserArrayAdapter extends ArrayAdapter<Account> {
    Activity context;
    int idLayout;
    ArrayList<Account> listUser;

    public UserArrayAdapter(@NonNull Activity context, int idLayout, ArrayList<Account> listUser) {
        super(context,idLayout, listUser);
        this.context = context;
        this.idLayout = idLayout;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myflater= context.getLayoutInflater();
        convertView = myflater.inflate(idLayout, null);
//        int width=parent.getWidth()/3;
//        convertView.setLayoutParams(new GridView.LayoutParams(width,GridView.AUTO_FIT));
        Account account = listUser.get(position);
        TextView textView = convertView.findViewById(R.id.tvnameTTU);
        textView.setText(account.getName());
        TextView textView1 = convertView.findViewById(R.id.tvUserTTU);
        textView.setText(account.getUsername());
        TextView textView2 = convertView.findViewById(R.id.tvPasswordTTU);
        textView.setText(account.getPasswword());
        return convertView;
    }
}
