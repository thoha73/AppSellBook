package com.example.appsellbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsellbook.GraphQL.GraphQLApiService;
import com.example.appsellbook.GraphQL.GraphQLRequest;
import com.example.appsellbook.GraphQL.GraphQLResponse;
import com.example.appsellbook.GraphQL.RetrofitClient;
import com.example.appsellbook.R;
import com.example.appsellbook.adapter.BookArrayAdapter;
import com.example.appsellbook.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity {

//    int image1[]={R.drawable.book9,R.drawable.book7,R.drawable.book8,R.drawable.book10};
//    String nameBook1[]={"Đắc nhân tâm","Chí phèo","Tài chính","Think & grow rich"};
//    String author1[] ={"Bill Gate","Thị Nở","Phạm Nhật Vượng","Đặng Khánh Vân"};
//    double price1[]={150000,500000,999999,8888888};
//    String description1[]={"Xin chào","Xin chào","Xin chào","Xin chào"};
//    String ISBN1[]={"12312312","123124151","151251251","124125412"};
//    int image2[]={R.drawable.book1,R.drawable.book3,R.drawable.book4,R.drawable.book5};
//    String nameBook2[]={"Sách động lực","Sách hư cấu","Sách tiểu thuyết","Sách kinh tế"};
//    String author2[] ={"Jackma","Võ Nghịch Tiên","Lý Mộ Uyển","Elon Musk"};
//    double price2[]={111000,1000000,676767,12315488};
//    String description2[]={"Hello ","Hello","Method...","Hello"};
//    String ISBN2[]={"345634643","5236346","1523562352","57345435"};
    GridView gridview1,gridview2,gridview3;
    TextView tv_new,tv_popular,tv_category;
    ArrayList<Book> listBook,listBook1,listBook2;
    LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
    BookArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
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
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        tv_category.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,AllCategories.class));
        });
        tv_popular.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,Popular.class));
        });
        tv_new.setOnClickListener(view -> {
            startActivity(new Intent(Home.this,NewProduct.class));
        });

        llHome.setOnClickListener(view -> {

        });
        llNotification.setOnClickListener(view -> {
           startActivity(new Intent(Home.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
           startActivity(new Intent(Home.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
           startActivity(new Intent(Home.this,Settings.class));
        });
        llProfile.setOnClickListener(view -> {
           startActivity(new Intent(Home.this,Profile.class));
        });

//        Demo Graphql
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " query{\n" +
                "    books(order: [ {\n" +
                "       bookId: DESC\n" +
                "    }]){\n" +
                "        bookId\n" +
                "        bookName\n" +
                "        isbn\n" +
                "        listedPrice\n" +
                "        sellPrice\n" +
                "        quantity\n" +
                "        description\n" +
                "        author{\n" +
                "          authorId\n" +
                "          authorName\n" +
                "        }\n" +
                "        rank\n" +
                "        images{\n" +
                "            imageId\n" +
                "            imageName\n" +
                "            imageData\n" +
                "            icon\n" +
                "        }\n" +
                "        \n" +
                "    }\n" +
                "}";
        GraphQLRequest request = new GraphQLRequest(query);
        Log.d("GraphQL", "Request: " + request.getQuery());
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data != null && data.getData() instanceof Map) {
                        Map<String, Object> responseData = (Map<String, Object>) data.getData();
                        Object booksData = responseData.get("books");
                        if (booksData instanceof List) {
                            List<Map<String, Object>> booksList = (List<Map<String, Object>>) booksData;

                            List<com.example.appsellbook.DTOs.Book> books = new ArrayList<>();
                            for (Map<String, Object> bookMap : booksList) {
                                com.example.appsellbook.DTOs.Book book = new com.example.appsellbook.DTOs.Book();

                                Object bookId = bookMap.get("bookId");
                                if (bookId instanceof Number) {
                                    book.setBookId(((Number) bookId).intValue());
                                }

                                book.setBookName((String) bookMap.get("bookName"));
                                book.setDescription((String) bookMap.get("description"));
                                if (bookMap.get("images") instanceof List) {
                                    List<Map<String, Object>> imagesList = (List<Map<String, Object>>) bookMap.get("images");
                                    List<com.example.appsellbook.DTOs.Image> images = new ArrayList<>();
                                    for (Map<String, Object> imageMap : imagesList) {
                                        com.example.appsellbook.DTOs.Image image = new com.example.appsellbook.DTOs.Image();
                                        image.setImageData((String) imageMap.get("imageData"));
                                        images.add(image);
                                    }
                                    book.setImages(images);
                                }
                                books.add(book);
                            }

                            if (!books.isEmpty() && books!= null) {
                                initGridView1(books, gridview1);
                                gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        Log.d("GridView", "Item clicked: " + position);
                                        com.example.appsellbook.DTOs.Book selectedBook = books.get(position);
                                        if (selectedBook != null) {
                                            Intent intent = new Intent(Home.this, ProductDetail.class);
                                            intent.putExtra("BookId", selectedBook.getBookId());
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                Log.d("GraphQL", "Danh sách sách không có dữ liệu.");
                            }
                        } else {
                            Log.d("GraphQL", "Không thể lấy danh sách sách từ dữ liệu trả về.");
                        }
                    } else {
                        Log.d("GraphQL", "Dữ liệu trả về không phải là Map.");
                    }
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", t.getMessage());
            }
        });

    }
    private void initGridView(ArrayList<Book> list,int img[],String nameBook[],String author[],double price[],String description[],String ISBN[],GridView grv){
        grv.setPadding(10,10,10,20);
        list=new ArrayList<>();
        for(int i=0;i<img.length;i++){
            list.add(new Book(img[i],nameBook[i],author[i],price[i],description[i],ISBN[i]));
        }
//        myAdapter = new BookArrayAdapter(this,R.layout.layout_item_book,list);
        grv.setAdapter(myAdapter);
    }
    private void initGridView1(List<com.example.appsellbook.DTOs.Book> list,GridView grv){
        grv.setPadding(10,10,10,20);
        ArrayList<com.example.appsellbook.DTOs.Book> books=new ArrayList<com.example.appsellbook.DTOs.Book>(list);
        myAdapter = new BookArrayAdapter(this,R.layout.layout_item_book,books);
        grv.setAdapter(myAdapter);
    }


}