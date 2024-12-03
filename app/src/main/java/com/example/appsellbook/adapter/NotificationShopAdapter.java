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

import com.example.appsellbook.DTOs.Notification;
import com.example.appsellbook.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationShopAdapter extends ArrayAdapter<Notification> {

    private Context Context;
    private int Resource;
    private List<Notification> notiList;
//    private Book[] imageList; // Mảng chứa các hình ảnh

    public NotificationShopAdapter(@NonNull Context context, int resource, @NonNull List<Notification> name) {
        super(context, resource, name);
        this.Context = context;
        this.Resource = resource;
        this.notiList = name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Notification name = notiList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(Context);
            convertView = inflater.inflate(Resource, parent, false);
        }
//        ImageView imgUserIcon = convertView.findViewById(R.id.imgUserNotification);
        TextView tvName = convertView.findViewById(R.id.tv_content);
        TextView tvDay = convertView.findViewById(R.id.tv_day);

        //  imgUserIcon.setImageResource(R.drawable.account); // Đặt hình ảnh của người dùng từ mảng Book
        tvName.setText(name.getContext());
        SimpleDateFormat df= new SimpleDateFormat("dd 'thg' MM", new Locale("vi","VN"));
        tvDay.setText(df.format(name.getCreatedDate()));

        return convertView;
    }
}