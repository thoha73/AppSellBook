package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookArrayAdapter;
import com.example.appsellbook.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home1 extends AppCompatActivity {
    String role;
    int image[]={R.drawable.book11,R.drawable.book13,R.drawable.book6,R.drawable.book12};
    String nameBook[]={"English","Nhật ký của tôi","Mỗi lần vấp ngã là một lần trưởng thành","Thành phố phép màu"};
    String author[] ={"Jason","Nguyễn Nhật Ánh","Trần Văn An","Đặng Khánh Vân"};
    double price[]={150000,500000,999999,8888888};
    String description[]={"Xin chào","Xin chào","Xin chào","Xin chào"};
    String ISBN[]={"12312312","123124151","151251251","124125412"};

    int image1[]={R.drawable.book9,R.drawable.book7,R.drawable.book8,R.drawable.book10};
    String nameBook1[]={"Đắc nhân tâm","Chí phèo","Tài chính","Think & grow rich"};
    String author1[] ={"Bill Gate","Thị Nở","Phạm Nhật Vượng","Đặng Khánh Vân"};
    double price1[]={150000,500000,999999,8888888};
    String description1[]={"Xin chào","Xin chào","Xin chào","Xin chào"};
    String ISBN1[]={"12312312","123124151","151251251","124125412"};
    int image2[]={R.drawable.book1,R.drawable.book3,R.drawable.book4,R.drawable.book5};
    String nameBook2[]={"Sách động lực","Sách hư cấu","Sách tiểu thuyết","Sách kinh tế"};
    String author2[] ={"Jackma","Võ Nghịch Tiên","Lý Mộ Uyển","Elon Musk"};
    double price2[]={111000,1000000,676767,12315488};
    String description2[]={"Hello ","Hello","Method...","Hello"};
    String ISBN2[]={"345634643","5236346","1523562352","57345435"};
    GridView gridview1,gridview2,gridview3;
    TextView tv_new,tv_popular,tv_category;
    ArrayList<Book> listBook,listBook1,listBook2;
    LinearLayout llHome,llNotification,llSetting,llSearch,llProduct;
    BookArrayAdapter myAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_new=findViewById(R.id.tv_showallNew);
        tv_popular=findViewById(R.id.tv_showallPopular);
        tv_category=findViewById(R.id.tv_showallCategory);
        gridview1=findViewById(R.id.gridview1);
        gridview2=findViewById(R.id.gridview2);
        gridview3=findViewById(R.id.gridview3);


        initGridView(listBook,image,nameBook,author,price,description,ISBN,gridview1);
        initGridView(listBook1,image1,nameBook1,author1,price1,description1,ISBN1,gridview2);
        initGridView(listBook2,image2,nameBook2,author2,price2,description2,ISBN2,gridview3);

        tv_category.setOnClickListener(view -> {
            startActivity(new Intent(Home1.this,AllCategories.class));
        });
        tv_popular.setOnClickListener(view -> {
            startActivity(new Intent(Home1.this,Popular.class));
        });
        tv_new.setOnClickListener(view -> {
            startActivity(new Intent(Home1.this,NewProduct.class));
        });
        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Home1.this,ProductDetail.class);
                intent.putExtra("image",image[position]);
                intent.putExtra("name",nameBook[position]);
                intent.putExtra("author",author[position]);
                intent.putExtra("price",price[position]);
                intent.putExtra("description",description[position]);
                intent.putExtra("ISBN",ISBN[position]);
                intent.putExtra("source","gridview1");
                startActivity(intent);
            }
        });
        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Home1.this,ProductDetail.class);
                intent.putExtra("image1",image1[position]);
                intent.putExtra("name1",nameBook1[position]);
                intent.putExtra("author1",author1[position]);
                intent.putExtra("price1",price1[position]);
                intent.putExtra("description1",description1[position]);
                intent.putExtra("ISBN1",ISBN1[position]);
                intent.putExtra("source","gridview2");
                startActivity(intent);
            }
        });
        gridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent2 = new Intent(Home1.this,ProductDetail.class);
                intent2.putExtra("image2",image2[position]);
                intent2.putExtra("name2",nameBook2[position]);
                intent2.putExtra("author2",author2[position]);
                intent2.putExtra("price2",price2[position]);
                intent2.putExtra("description2",description2[position]);
                intent2.putExtra("ISBN2",ISBN2[position]);
                startActivity(intent2);
            }
        });

        BottomNavigationView bottom_NavigationView2;
        bottom_NavigationView2 = findViewById(R.id.bottom_navigation2);
        bottom_NavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menu_home){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_notification){
                    startActivity(new Intent(getApplicationContext(), OwnerNotification.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_search){
                    startActivity(new Intent(getApplicationContext(), Home1.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_setting){
                    startActivity(new Intent(getApplicationContext(), ShopOwner.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                if(id==R.id.menu_product){
                    startActivity(new Intent(getApplicationContext(), PostNewProduct.class));
                    overridePendingTransition(0,0);
                    return  true;
                }
                return false;
            }
        });

    }
    private void initGridView(ArrayList<Book> list,int img[],String nameBook[],String author[],double price[],String description[],String ISBN[],GridView grv){
        grv.setPadding(10,10,10,20);
        list=new ArrayList<>();
        for(int i=0;i<img.length;i++){
            list.add(new Book(img[i],nameBook[i],author[i],price[i],description[i],ISBN[i]));
        }
        myAdapter = new BookArrayAdapter(this,R.layout.layout_item_book,list);
        grv.setAdapter(myAdapter);
    }
    private void navigateToSettings() {
        if ("admin".equals(role)) {
            startActivity(new Intent(Home1.this, AdminDashboard.class));
        } else {
            startActivity(new Intent(Home1.this, ShopOwner.class));
        }
    }

}