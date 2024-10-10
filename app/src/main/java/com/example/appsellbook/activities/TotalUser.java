package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import com.example.appsellbook.R;
import com.example.appsellbook.model.User;

public class TotalUser extends AppCompatActivity{
    ImageView img_back,img_logUser,img_logUser1,img_logUser2,img_logUser3,img_logUser4;
    TextView tv_change,tv_change1,tv_change2,tv_change3,tv_change4;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_user);
        User[] users = new User[]{
                new User("Võ Vĩ Khương", "vikhuong92", "123"),
                new User("Nguyễn Thọ Hà", "thoha73", "123"),
                new User("Phan Anh Duy", "pad76", "123"),
                new User("Trần Thanh Vỹ", "thanhvy92", "123"),
                new User("Quang Thảo", "quangthao75", "123")
        };
        displayUser(users[0], R.id.textView_nameTTU, R.id.textView_UsernameTTU, R.id.edittextPasswordTTU);
        displayUser(users[1], R.id.textView_nameTTU1, R.id.textView_UsernameTTU1, R.id.edittextPasswordTTU1);
        displayUser(users[2], R.id.textView_nameTTU2, R.id.textView_UsernameTTU2, R.id.edittextPasswordTTU2);
        displayUser(users[3], R.id.textView_nameTTU3, R.id.textView_UsernameTTU3, R.id.edittextPasswordTTU3);
        displayUser(users[4], R.id.textView_nameTTU4, R.id.textView_UsernameTTU4, R.id.edittextPasswordTTU4);
        img_back = findViewById(R.id.imgView_TotalUserback);
        img_logUser = findViewById(R.id.imglogUser);
        img_logUser1 = findViewById(R.id.imglogUser1);
        img_logUser2 = findViewById(R.id.imglogUser2);
        img_logUser3 = findViewById(R.id.imglogUser3);
        img_logUser4 = findViewById(R.id.imglogUser4);
        tv_change = findViewById(R.id.tv_change);
        tv_change1 = findViewById(R.id.tv_change1);
        tv_change2 = findViewById(R.id.tv_change2);
        tv_change3 = findViewById(R.id.tv_change3);
        tv_change4 = findViewById(R.id.tv_change4);


        img_back.setOnClickListener(v->finish());
        img_logUser.setOnClickListener(v -> showConfirmationDialog(img_logUser));
        img_logUser1.setOnClickListener(v -> showConfirmationDialog(img_logUser1));
        img_logUser2.setOnClickListener(v -> showConfirmationDialog(img_logUser2));
        img_logUser3.setOnClickListener(v -> showConfirmationDialog(img_logUser3));
        img_logUser4.setOnClickListener(v -> showConfirmationDialog(img_logUser4));
        tv_change.setOnClickListener(v->{
            Intent intent = new Intent(TotalUser.this, ChangePasswordUser.class);
            startActivity(intent);
        });
        tv_change1.setOnClickListener(v->{
            Intent intent = new Intent(TotalUser.this, ChangePasswordUser.class);
            startActivity(intent);
        });
        tv_change2.setOnClickListener(v->{
            Intent intent = new Intent(TotalUser.this, ChangePasswordUser.class);
            startActivity(intent);
        });
        tv_change3.setOnClickListener(v->{
            Intent intent = new Intent(TotalUser.this, ChangePasswordUser.class);
            startActivity(intent);
        });
        tv_change4.setOnClickListener(v->{
            Intent intent = new Intent(TotalUser.this, ChangePasswordUser.class);
            startActivity(intent);
        });

//        btn_ok.setOnClickListener(v ->{
//            setContentView(R.layout.activity_change_password_user);
//        } );
    }

    private void displayUser(User user, int nameId, int usernameId, int passwordId) {
        TextView nameTextView = findViewById(nameId);
        TextView usernameTextView = findViewById(usernameId);
        EditText passwordEditText = findViewById(passwordId);

        nameTextView.setText("Name: " + user.getName());
        usernameTextView.setText("Username: " + user.getUsername());
        passwordEditText.setText(user.getPasswword());
    }
    private void showConfirmationDialog(ImageView clickedImageView) {
        // Tạo mới hộp thoại user
        Dialog dialog = new Dialog(TotalUser.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog);

        Button buttonYes = dialog.findViewById(R.id.buttonYes);
        Button buttonNo = dialog.findViewById(R.id.buttonNo);

        // Xử lý sự kiện khi nhấn Yes hoặc No
        buttonYes.setOnClickListener(v -> {
            clickedImageView.setImageResource(R.drawable.locked_user);
            dialog.dismiss();
        });

        buttonNo.setOnClickListener(v -> {
            clickedImageView.setImageResource(R.drawable.lock_open);
            dialog.dismiss();
        });

        dialog.show();
    }

}
