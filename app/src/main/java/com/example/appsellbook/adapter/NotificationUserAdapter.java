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
import com.example.appsellbook.model.Notification;

import java.util.List;
import java.util.zip.Inflater;

public class NotificationUserAdapter extends ArrayAdapter<Notification> {
    private Context myContext;
    private int resource;
    private List<Notification> listNotification;
    public NotificationUserAdapter(Context myContext, int resource, List<Notification> listNotification){
        super(myContext,resource,listNotification);
        this.myContext=myContext;
        this.resource=resource;
        this.listNotification=listNotification;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
         convertView= LayoutInflater.from(myContext).inflate(R.layout.layout_item_notification,parent,false);
         viewHolder= new ViewHolder();
         viewHolder.tv_title=convertView.findViewById(R.id.textView_titleNotification);
         viewHolder.tv_time=convertView.findViewById(R.id.textView_time);
         viewHolder.img_read=convertView.findViewById(R.id.imageV_circle);
         convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        Notification notification=listNotification.get(position);
        viewHolder.tv_title.setText(notification.getTitle());
        viewHolder.tv_time.setText(notification.getCreatedDate());
        if(notification.isRead()){
            viewHolder.img_read.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.img_read.setVisibility(View.VISIBLE);
        }

        return convertView;

    }
    public class ViewHolder{
        TextView tv_title;
        TextView tv_time;
        ImageView img_read;
    }
}
