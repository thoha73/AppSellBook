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
import com.example.appsellbook.model.User;
import com.example.appsellbook.model.Book;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<User> {

    private Context Context;
    private int Resource;
    private List<User> userList;
    private Book[] imageList; // Mảng chứa các hình ảnh

    public NotificationAdapter(@NonNull Context context, int resource, @NonNull List<User> name, Book[] images) {
        super(context, resource, name);
        this.Context = context;
        this.Resource = resource;
        this.userList = name;
        this.imageList = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User name = userList.get(position);
        Book userImage = imageList[position];
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(Context);
            convertView = inflater.inflate(Resource, parent, false);
        }
        ImageView imgUserIcon = convertView.findViewById(R.id.imgUserNotification);
        TextView tvName = convertView.findViewById(R.id.tv_content);
        TextView tvDay = convertView.findViewById(R.id.tv_day);
        imgUserIcon.setImageResource(userImage.getImage()); // Đặt hình ảnh của người dùng từ mảng Book
        tvName.setText(name.getName() + " submitted one review for an order");
        tvDay.setText("10 thg 10");

        return convertView;
    }
}
