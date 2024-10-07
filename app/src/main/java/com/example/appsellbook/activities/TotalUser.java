package com.example.appsellbook.activities;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookArrayAdapter;
import com.example.appsellbook.adapter.UserArrayAdapter;
import com.example.appsellbook.model.Account;

import java.util.ArrayList;
import java.util.List;

public class TotalUser extends AppCompatActivity {
    TextView tv_back,tv_username,tv_password,tv_name;
    ArrayList<Account> listUser;
    UserArrayAdapter myAdapter;
    private List<Account> getData(){
        List<Account> list = new ArrayList<>();
        list.add(new Account("Võ Vĩ Khương","vikhuong92","******"));
        list.add(new Account("Nguyễn Thọ hà","thoha73","******"));
        list.add(new Account("Phan Anh Duy","pad76","******"));
        list.add(new Account("Trần Thanh Vỹ","thanhvy92","******"));
        list.add(new Account("Trương Như Quang Thảo","quangthao75","******"));
        return list;
    }

}
