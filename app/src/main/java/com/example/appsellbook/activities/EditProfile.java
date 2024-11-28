package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditProfile extends BaseActivity {
    EditText edt_fullname, edt_dateofbirth, edt_email, edt_address, edt_phone;
    RadioButton radio_nam, radio_nu;
    Button buttonSave;
    @SuppressLint("MissinginFlatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_fullname=findViewById(R.id.edt_fullname);
        edt_dateofbirth=findViewById(R.id.edt_dateofbirth);
        edt_email=findViewById(R.id.edt_email);
        edt_address=findViewById(R.id.edt_address);
        edt_phone=findViewById(R.id.edt_phone);
        radio_nam=findViewById(R.id.radio_nam);
        radio_nu=findViewById(R.id.radio_nu);
        buttonSave=findViewById(R.id.buttonSave);


        ImageView img_back;
        img_back = findViewById(R.id.imageV_back);
        img_back.setOnClickListener(view -> finish());

        BottomNavigationView bottom_NavigationView;
        bottom_NavigationView = findViewById(R.id.bottom_navigation);
        bottom_NavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), Notification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_profile){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });
        buttonSave.setOnClickListener(view -> {
//            String gender="";
//            if((edt_fullname.getText().toString().isEmpty())
//                    ||(edt_dateofbirth.getText().toString().isEmpty())
//                    ||(edt_email.getText().toString().isEmpty())
//                    ||(edt_address.getText().toString().isEmpty())
//                    ||(edt_phone.getText().toString().isEmpty())){
//                new AlertDialog.Builder(this)
//                        .setTitle("Thông báo")
//                        .setMessage("Vui lònd điền đầy đủ thông tin!")
//                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
//                        .show();
//            }else {
//                if (radio_nam.isChecked()) {
//                    gender = "Nam";
//                } else if (radio_nu.isChecked()) {
//                    gender = "Nữ";
//                }
//                new AlertDialog.Builder(this)
//                        .setTitle("Thông báo")
//                        .setMessage("Cập nhật thành công!")
//                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
//                        .show();
//            }
        });
    }
}