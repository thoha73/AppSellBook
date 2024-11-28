package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.Utils.SessionManager;
import com.example.appsellbook.graphql.GraphQLApiService;
import com.example.appsellbook.graphql.GraphQLRequest;
import com.example.appsellbook.graphql.GraphQLResponse;
import com.example.appsellbook.graphql.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            String email="";
            String fullName="";
            String dateOfBirth=edt_dateofbirth.getText().toString();
            String phone="";
            String gender="";
            String address="";
            try {
                if(dateOfBirth == null || !dateOfBirth.matches("\\d{2}/\\d{2}/\\d{4}")){
                    new AlertDialog.Builder(this)
                            .setTitle("Thông báo")
                            .setMessage("Vui lòng nhập đúng định dạng ngày sinh (dd/MM/yyy)!")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            }catch (Exception ex){
                Log.e("Exception", ex.getMessage());
            }
            if((edt_fullname.getText().toString().isEmpty())
                    ||(edt_dateofbirth.getText().toString().isEmpty())
                    ||(edt_email.getText().toString().isEmpty())
                    ||(edt_address.getText().toString().isEmpty())
                    ||(edt_phone.getText().toString().isEmpty())){
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Vui lònd điền đầy đủ thông tin!")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();



            }else {
                if (radio_nam.isChecked()) {
                    gender = "Nam";
                } else if (radio_nu.isChecked()) {
                    gender = "Nữ";
                }
                fullName=edt_fullname.getText().toString();
                dateOfBirth=edt_dateofbirth.getText().toString();
                email=edt_email.getText().toString();
                phone=edt_phone.getText().toString();
                address=edt_address.getText().toString();
                UpdateInformation(email,fullName,dateOfBirth,address,phone,gender);
            }
        });
    }
    public void UpdateInformation(String email,String fullName, String dateOfBirth, String address, String phone, String gender){
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());  // Định dạng đầu vào
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());  // Định dạng đầu ra

        SessionManager sessionManager = new SessionManager(this);
        int userId = sessionManager.getUserId();

        Date date = null;
        try {
            date = inputFormat.parse(dateOfBirth);  // Chuyển đổi chuỗi thành đối tượng Date
        } catch (ParseException e) {
            e.printStackTrace();  // Xử lý lỗi nếu chuỗi không hợp lệ
        }

        String formattedDate = null;
        if (date != null) {
            formattedDate = outputFormat.format(date);  // Định dạng lại ngày thành định dạng ISO
        } else {
            // Xử lý nếu ngày không hợp lệ
            Log.e("Update Error", "Ngày sinh không hợp lệ");
        }
        // Tạo GraphQL query
        String query = "mutation{\n" +
                "  update(userId:"+userId+",registerInfor:  {\n" +
                "     dateOfBirth: \""+formattedDate+"\"\n" +
                "     email: \""+email+"\",\n" +
                "     firstName: \""+fullName+"\"\n" +
                "     lastName: \""+fullName+"\"\n" +
                "     gender: \""+gender+"\"\n" +
                "     phone: \""+phone+"\"\n" +
                "     purchaseAddress: \""+address+"\"\n" +
                "     deliveryAddress: \""+address+"\"\n" +
                "  }){\n" +
                "    username\n" +
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        Log.e("Update", "Query: " + query);

        // Gửi yêu cầu
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> responseData = response.body();
                    Object data = responseData.getData();

                    if (data instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data;
                        Object userObject = dataMap.get("update");  // Đảm bảo key là "update"

                        if (userObject instanceof LinkedTreeMap) {
                            LinkedTreeMap<String, Object> userMap = (LinkedTreeMap<String, Object>) userObject;

                            // Trích xuất thông tin từ phản hồi
                            String returnedUsername = (String) userMap.get("username");

                            // Thông báo cập nhật thành công
                            new AlertDialog.Builder(EditProfile.this)
                                    .setTitle("Thông báo")
                                    .setMessage("Cập nhật thông tin thành công!")
                                    .setPositiveButton("OK", (dialog, which) ->{
                                        dialog.dismiss();
                                        Intent intent = new Intent(EditProfile.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    } )
                                    .show();
                        } else {
                            Log.e("Update Error", "Không tìm thấy thông tin 'update' trong phản hồi.");
                        }
                    } else {
                        Log.e("Update Error", "Dữ liệu phản hồi không đúng định dạng.");
                    }
                } else {
                    Log.e("Update Error", "Phản hồi thất bại: " + response.message());
                    Toast.makeText(getApplicationContext(), "Cập nhật thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("Update Failure", "Lỗi khi gửi yêu cầu", t);
                Toast.makeText(getApplicationContext(), "Cập nhật thất bại, kiểm tra kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    };
}