package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;

public class ForgetPassword extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText edt_username, edt_password, edt_confirmpass;
        Button btn_next;
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_confirmpass = findViewById(R.id.edt_confirmpassword);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(view -> {
            if(edt_username.getText().toString().isEmpty()) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Please enter username");
                alertDialog.show();

            }else {
                if(edt_username.getText().toString().length()<6){
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Username must be at least 6 characters");
                    alertDialog.show();
                }else if (edt_username.getText().toString().equals("Thoha2004")){
                    edt_username.setVisibility(View.GONE);
                    edt_password.setVisibility(View.VISIBLE);
                    edt_confirmpass.setVisibility(View.VISIBLE);
                    btn_next.setText("RESET");
                }
            }



        });
        edt_username.setOnFocusChangeListener((view, b) -> {
            setFocus(edt_username,b,"Username");
      });
        edt_password.setOnFocusChangeListener((view, b) -> {
            setFocus(edt_password,b,"Password");
        });
        edt_password.setOnTouchListener((view, motionEvent) -> {
            return hidenPassword(edt_password, motionEvent);
        });
        edt_confirmpass.setOnFocusChangeListener((view, b) -> {
            setFocus(edt_confirmpass,b,"Confirm password");
        });
        edt_confirmpass.setOnTouchListener((view, motionEvent) -> {
          return hidenPassword(edt_confirmpass,motionEvent)  ;
        });


    }
    private boolean hidenPassword(EditText edt, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(event.getRawX()>=edt.getRight()-(edt.getCompoundDrawables()[2].getBounds().width()+20)){
                if(edt.getTransformationMethod() instanceof PasswordTransformationMethod){
                    edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Drawable drawableR=getResources().getDrawable(R.drawable.eye_slash_solid);
                    Drawable drawableL=getResources().getDrawable(R.drawable.lock_solid);
                    drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
                    drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                    edt.setCompoundDrawables(drawableL,null,drawableR,null);
                    return true;
                }else{
                    edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Drawable drawableR=getResources().getDrawable(R.drawable.eye_solid);
                    Drawable drawableL=getResources().getDrawable(R.drawable.lock_solid);
                    drawableR.setBounds(0,0,drawableR.getIntrinsicWidth(),drawableR.getIntrinsicHeight());
                    drawableL.setBounds(0,0,drawableL.getIntrinsicWidth(),drawableL.getIntrinsicHeight());
                    edt.setCompoundDrawables(drawableL,null,drawableR,null);
                    return true;
                }
            }
        }
        edt.setSelection(edt.getText().length());
        return false;
    };
    private void setFocus(View v,Boolean hasFocus, String hint){
        if(v instanceof EditText){
            EditText edt=(EditText) v;
            if(hasFocus){
                edt.setHint("");
            }else{
                edt.setHint(hint);
            }
        }

    }
}