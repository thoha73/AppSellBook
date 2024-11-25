package com.example.appsellbook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsellbook.DTOs.Author;
import com.example.appsellbook.DTOs.Book;
import com.example.appsellbook.DTOs.Category;
import com.example.appsellbook.DTOs.Commentation;
import com.example.appsellbook.DTOs.User;
import com.example.appsellbook.GraphQL.GraphQLApiService;
import com.example.appsellbook.GraphQL.GraphQLBookResponse;
import com.example.appsellbook.GraphQL.GraphQLRequest;
import com.example.appsellbook.GraphQL.GraphQLResponse;
import com.example.appsellbook.GraphQL.RetrofitClient;
import com.example.appsellbook.R;
import com.example.appsellbook.adapter.CommentArrayAdapter;
import com.example.appsellbook.utils.CurrencyFormat;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail  extends AppCompatActivity {
    private TextView tv_nameBook,tv_price,tv_author,tv_isbn,tv_description,tv_category,tv_publisher;
    private ImageView img,img_Home,img_Notification,img_Search,img_Setting,img_Profile;
    private RecyclerView recyclerView;
    private CommentArrayAdapter commentArrayAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        img = findViewById(R.id.imageViewAvatar);
        if (img == null) {
            Log.e("ProductDetail", "ImageView not found with ID imageView1");
        }
        tv_nameBook =findViewById(R.id.textViewNameProduct);
        tv_price = findViewById(R.id.textViewPriceProduct);
        tv_author= findViewById(R.id.textViewContentAuthor);
        tv_description = findViewById(R.id.textViewContentDescription);
        tv_isbn = findViewById(R.id.textViewContentISBN);
        tv_category = findViewById(R.id.textViewContentCategory);
        tv_publisher = findViewById(R.id.textViewContentPublisher);
        recyclerView = findViewById(R.id.listview_comment);


        LinearLayout llHome,llNotification,llSetting,llSearch,llProfile;
        llHome=findViewById(R.id.ll_home);
        llNotification=findViewById(R.id.ll_notification);
        llSearch=findViewById(R.id.ll_search);
        llSetting=findViewById(R.id.ll_settings);
        llProfile=findViewById(R.id.ll_profile);
        llHome.setOnClickListener(view -> {
            startActivity(new Intent(ProductDetail.this,Home.class));
        });

        llNotification.setOnClickListener(view -> {
            startActivity(new Intent(ProductDetail.this,Notification.class));
        });
        llSearch.setOnClickListener(view -> {
            startActivity(new Intent(ProductDetail.this,Home.class));

        });
        llSetting.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Setting.class));
        });
        llProfile.setOnClickListener(view -> {
//           startActivity(new Intent(Home.this,Profile.class));
        });

        int bookId = getIntent().getIntExtra("BookId", 0);
        if (bookId != 0) {
        fetchBookDetails(bookId);
        getCommentationInBook(bookId);
        }
    }
    private void fetchBookDetails(int bookId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = " query{\n" +
                "  bookById(id:"+bookId+"){\n" +
                "    bookId\n" +
                "    bookName\n" +
                "    isbn\n" +
                "    listedPrice\n" +
                "    sellPrice\n" +
                "    quantity\n" +
                "    description\n" +
                "    publisher\n" +
                "    rank\n" +
                "    images{\n" +
                "      imageId\n" +
                "      imageName\n" +
                "      imageData\n" +
                "      icon\n" +
                "    }\n" +
                "    author{\n" +
                "      authorName\n" +
                "    }\n"+
                "    categories{\n" +
                "      categoryId\n" +
                "      categoryName\n" +
                "    }"+
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();
                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> bookData = (LinkedTreeMap<String, Object>) data.getData();

                        // Assuming 'bookById' is in the map, extract it manually
                        LinkedTreeMap<String, Object> bookById = (LinkedTreeMap<String, Object>) bookData.get("bookById");

                        if (bookById != null) {
                            Book book = new Book();
                            book.setBookId(((Double) bookById.get("bookId")).intValue());
                            book.setBookName((String) bookById.get("bookName"));
                            book.setIsbn((String) bookById.get("isbn"));
                            book.setListedPrice(((Double) bookById.get("listedPrice")).floatValue());
                            book.setSellPrice(((Double) bookById.get("sellPrice")).floatValue());
                            book.setQuantity(((Double) bookById.get("quantity")).intValue());
                            book.setDescription((String) bookById.get("description"));
                            book.setPublisher((String) bookById.get("publisher"));
                            book.setRank(((Double) bookById.get("rank")).intValue());
                            List<LinkedTreeMap<String, Object>> images = (List<LinkedTreeMap<String, Object>>) bookById.get("images");
                            if (images != null && !images.isEmpty()) {
                                String base64Image = (String) images.get(0).get("imageData");
                                setImageFromBase64(base64Image);
                            }
                            LinkedTreeMap<String ,Object> author = (LinkedTreeMap<String, Object>) bookById.get("author");
                            if (author != null) {
                                Author authors= new Author();
                                String authorName = (String) author.get("authorName");
                                authors.setAuthorName(authorName);
                                book.setAuthor(authors);
                            }
                            List<LinkedTreeMap<String, Object>> categories = (List<LinkedTreeMap<String, Object>>) bookById.get("categories");
                            if(categories!=null && !categories.isEmpty()){
                                List<Category> categoryList = new ArrayList<>();
                                for(LinkedTreeMap<String, Object> category : categories){
                                  Category category1 = new Category();
                                  category1.setCategoryId(((Double)category.get("categoryId")).intValue());
                                  category1.setCategoryName((String)category.get("categoryName"));
                                  categoryList.add(category1);
                                };
                                book.setCategories(categoryList);
                            }
                        }
                    } else {
                        Log.d("Error", "Unexpected data format: " + data.getData());
                    }
                } else {
                    Log.d("Error", "Failed to load book details: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Error fetching book details", t);
            }
        });
    }
    private void setImageFromBase64(String base64Image) {
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedBitmap);
        }
    }
    private void getCommentationInBook(int bookId) {
        GraphQLApiService apiService = RetrofitClient.getClient(this).create(GraphQLApiService.class);
        String query = "query {\n" +
                "  commentationsByBookId(bookId: " + bookId + ") {\n" +
                "    content\n" +
                "    rank\n" +
                "    user {\n" +
                "      lastName\n" +
                "      firstName\n" +
                "    }\n" +
                "  }\n" +
                "}";

        GraphQLRequest request = new GraphQLRequest(query);
        apiService.executeQuery(request).enqueue(new Callback<GraphQLResponse<Object>>() {
            @Override
            public void onResponse(Call<GraphQLResponse<Object>> call, Response<GraphQLResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse<Object> data = response.body();

                    if (data.getData() instanceof LinkedTreeMap) {
                        LinkedTreeMap<String, Object> dataMap = (LinkedTreeMap<String, Object>) data.getData();
                        Object commentations = dataMap.get("commentationsByBookId");

                        if (commentations instanceof ArrayList) {
                            List<Commentation> commentationList = new ArrayList<>();
                            ArrayList<?> commentationArray = (ArrayList<?>) commentations;

                            for (Object commentObj : commentationArray) {
                                if (commentObj instanceof LinkedTreeMap) {
                                    LinkedTreeMap<String, Object> commentMap = (LinkedTreeMap<String, Object>) commentObj;

                                    // Tạo đối tượng Commentation
                                    Commentation commentation = new Commentation();
                                    commentation.setCommentationContent((String) commentMap.get("content"));
                                    commentation.setRank(((Double) commentMap.get("rank")).intValue());

                                    // Lấy thông tin người dùng
                                    Object userObj = commentMap.get("user");
                                    if (userObj instanceof LinkedTreeMap) {
                                        LinkedTreeMap<String, Object> userMap = (LinkedTreeMap<String, Object>) userObj;
                                        User user = new User();
                                        user.setFirstName((String) userMap.get("firstName"));
                                        user.setLastName((String) userMap.get("lastName"));
                                        commentation.setUser(user);
                                    }

                                    commentationList.add(commentation);
                                }
                            }

                            // Hiển thị danh sách bình luận bằng Adapter

                            recyclerView.setLayoutManager(new LinearLayoutManager(ProductDetail.this));
                            CommentArrayAdapter adapter = new CommentArrayAdapter(ProductDetail.this, new ArrayList<>(commentationList));
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("Error", "commentationsByBookId không phải là danh sách");
                        }
                    } else {
                        Log.e("Error", "Dữ liệu phản hồi không đúng định dạng: " + data.getData());
                    }
                } else {
                    Log.e("Error", "Phản hồi thất bại: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse<Object>> call, Throwable t) {
                Log.e("GraphQL Error", "Lỗi khi tải dữ liệu", t);
            }
        });
    }

}
